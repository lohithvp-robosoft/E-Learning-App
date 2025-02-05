package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.request.CreateQuestionRequest;
import com.robosoft.elearning.dto.request.UpdateQuestionRequest;
import com.robosoft.elearning.dto.response.*;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.modal.*;
import com.robosoft.elearning.repository.*;
import com.robosoft.elearning.services.FirebaseService;
import com.robosoft.elearning.services.QuestionServices;
import com.robosoft.elearning.util.EntityMapperUtil;
import com.robosoft.elearning.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;


@Service
public class QuestionServicesImpl implements QuestionServices {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private EntityMapperUtil entityMapperUtil;

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private UserTestProgressRepository userTestProgressRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private UserTestResultRepository userTestResultRepository;

    @Autowired
    private UserTestScoreRepository userTestScoreRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private FirebaseService firebaseService;

    @Value("${device.token}")
    private String deviceToken;

    @Value("${question.error.not-found}")
    private String questionNotFoundMessage;

    @Value("${test.error.not-found}")
    private String testNotFoundMessage;

    @Value("${message.error.userNotFound}")
    private String userNotFoundMessage;

    @Value("${notification.error.notSent}")
    private String notificationNotSentMessage;

    @Override
    public ResponseEntity<ResponseDTO<QuestionsListResponse>> beginTheTest(Long testId, HttpServletRequest request) {
        List<Question> questionsList = questionRepository.findByTestId(testId);
        User user = jwtUtils.getUserDataFromRequest(request);
        Test test = testRepository.findById(testId).orElseThrow(() -> new NotFoundException(testNotFoundMessage));

        UserTestProgress userTestProgress = new UserTestProgress(user, test);

        if (questionsList.isEmpty()) {
            throw new NotFoundException(questionNotFoundMessage);
        }

//        sendTestStartNotification(user);

        List<QuestionResponse> questionResponse = questionsList.stream()
                .map(question -> entityMapperUtil.convertToQuestionResponse(question))
                .toList();

        userTestProgressRepository.save(userTestProgress);

        Lesson lesson = test.getLesson();
        String testName = test.getHeading();
        Long lessonId = lesson.getId();
        Long chapterId = lesson.getChapter().getId();
        Long subjectId = lesson.getChapter().getSubject().getId();

        Chapter chapter = lesson.getChapter();
        Subject subject = chapter.getSubject();

        List<Lesson> lessons = chapter.getLessons().stream()
                .sorted(Comparator.comparing(Lesson::getId))
                .toList();
        int lessonIndex = lessons.indexOf(lesson) + 1;

        List<Chapter> chapters = subject.getChapters().stream()
                .sorted(Comparator.comparing(Chapter::getId))
                .toList();
        int chapterIndex = chapters.indexOf(chapter) + 1;

        QuestionsListResponse questionsListResponse = new QuestionsListResponse(test.getId(), chapterIndex,lessonIndex,testName,test.getQuestions().size(),questionResponse, test.getTotalTime());
        return responseUtil.successResponse(questionsListResponse);
    }

    @Override
    public ResponseEntity<ResponseDTO<Void>> saveOptionForAQuestion(Long testId, Long questionId, Integer selectedOption, HttpServletRequest request) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new NotFoundException(questionNotFoundMessage));
        if (question.getTest() == null || !question.getTest().getId().equals(testId)) {
            throw new NotFoundException(questionNotFoundMessage);
        }
        User user = jwtUtils.getUserDataFromRequest(request);
        UserTestProgress userTestProgress = userTestProgressRepository
                .findByUserIdAndTestId(user.getId(), testId)
                .orElseThrow(() -> new NotFoundException(userNotFoundMessage));

        saveOrRemoveSelectedOption(userTestProgress, question, questionId, selectedOption);

        userTestProgressRepository.save(userTestProgress);
        return responseUtil.successResponse(null);
    }

    private void sendTestStartNotification(User user) {
        if (user.isNotificationEnabled()) {
            String title = "Test Started";
            String body = "Your test has started. Good luck!";
            try {
                firebaseService.sendPushNotification(user.getDeviceToken(), title, body, user.getId());
            } catch (Exception e) {
                throw new RuntimeException(notificationNotSentMessage, e);
            }
        }
    }

    private void saveOrRemoveSelectedOption(UserTestProgress userTestProgress, Question question, Long questionId, Integer selectedOption) {
        if (selectedOption != null && selectedOption > 0 && selectedOption < 5) {
            if (question.getCorrectOption() == selectedOption) {
                userTestProgress.getCorrectlyAnsweredQuestionsId().add(questionId);
            }else{
                userTestProgress.getCorrectlyAnsweredQuestionsId().remove(questionId);
            }
            userTestProgress.getSelectedAnswers().put(questionId, selectedOption);
        } else {
            userTestProgress.getSelectedAnswers().remove(questionId);
            userTestProgress.getCorrectlyAnsweredQuestionsId().remove(questionId);
        }
    }


    @Override
    @Transactional
    public ResponseEntity<ResponseDTO<QuestionResponse>> createQuestion(CreateQuestionRequest request) {
        Test test = testRepository.findById(request.getTestId())
                .orElseThrow(() -> new NotFoundException(testNotFoundMessage));

        Question question = new Question();
        question.setTest(test);
        question.setQuestionStatement(request.getQuestionStatement());
        question.setQuestionImageUrl(request.getQuestionImageUrl());
        question.setCorrectOption(request.getCorrectOption());

        questionRepository.save(question);

        QuestionResponse response = entityMapperUtil.convertToQuestionResponse(question);
        return responseUtil.successResponse(response);
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseDTO<QuestionResponse>> updateQuestion(Long questionId, UpdateQuestionRequest request) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException(questionNotFoundMessage));

        question.setQuestionStatement(request.getQuestionStatement());
        question.setQuestionImageUrl(request.getQuestionImageUrl());
        question.setCorrectOption(request.getCorrectOption());

        questionRepository.save(question);

        QuestionResponse response = entityMapperUtil.convertToQuestionResponse(question);
        return responseUtil.successResponse(response);
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseDTO<Void>> deleteQuestion(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException(questionNotFoundMessage));

        questionRepository.delete(question);
        return responseUtil.successResponse(null);
    }

}



