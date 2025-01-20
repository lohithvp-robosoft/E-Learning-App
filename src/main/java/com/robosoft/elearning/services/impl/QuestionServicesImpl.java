package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.response.QuestionResponse;
import com.robosoft.elearning.dto.response.QuestionSetResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.TestSubmitResponse;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.modal.*;
import com.robosoft.elearning.repository.*;
import com.robosoft.elearning.services.QuestionServices;
import com.robosoft.elearning.util.EntityMapperUtil;
import com.robosoft.elearning.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    @Override
    public ResponseEntity<ResponseDTO<QuestionResponse>> beginTest(HttpServletRequest request, Long testId) {
        Question firstQuestion = questionRepository.findFirstByTestIdOrderByIdAsc(testId)
                .orElseThrow(() -> new NotFoundException("No questions found for the test with ID: " + testId));
        User user = jwtUtils.getUserDataFromRequest(request);
        Test test = testRepository.findById(testId).orElseThrow(()-> new NotFoundException("Test Not Found"));

        int totalQuestions = questionRepository.countByTestId(testId);
        Lesson lesson = test.getLesson();
        Long lessonId = lesson.getId();
        String lessonName = lesson.getLessonName();
        Long chapterId = lesson.getChapter().getId();
        Long subjectId = lesson.getChapter().getSubject().getId();

        int lessonIndex = lessonRepository.countByChapterIdAndIdLessThan(chapterId,lessonId) + 1;
        int chapterIndex = chapterRepository.countBySubjectIdAndIdLessThan(subjectId,chapterId) + 1;

        UserTestProgress userTestProgress = new UserTestProgress(user,test,firstQuestion.getId(),totalQuestions,lessonIndex,lessonName,chapterIndex);
        userTestProgressRepository.save(userTestProgress);

        QuestionResponse questionResponse = entityMapperUtil.convertToQuestionResponse(firstQuestion, null,1,totalQuestions,lessonIndex,lessonName, chapterIndex);


        return responseUtil.successResponse(questionResponse);
    }

    @Transactional(noRollbackFor = NotFoundException.class)
    @Override
    public ResponseEntity<ResponseDTO<QuestionResponse>> navigateAndSubmitAnswer(HttpServletRequest request,Long testId, Long nextQuestionId, Integer selectedOption, boolean isForward) {
        User user = jwtUtils.getUserDataFromRequest(request);
        UserTestProgress userTestProgress = userTestProgressRepository.findByUserIdAndTestId(user.getId(), testId).orElseThrow(() -> new NotFoundException("User test progress not found"));

        Question currentQuestion = questionRepository.findByIdAndTestId(userTestProgress.getCurrentQuestionId(), testId).orElseThrow(() -> new NotFoundException("Current Question Not Found"));

        if (selectedOption != null) {
            userTestProgress.getSelectedAnswers().put(currentQuestion.getId(), selectedOption);
            userTestProgress.setTotalAnsweredQuestions(userTestProgress.getSelectedAnswers().size());
            boolean wasCorrectBefore = userTestProgress.getCorrectlyAnsweredQuestionsId().contains(currentQuestion.getId());
            boolean isCorrectNow = selectedOption == currentQuestion.getCorrectOption();
            if (isCorrectNow && !wasCorrectBefore) {
                userTestProgress.getCorrectlyAnsweredQuestionsId().add(currentQuestion.getId());
                userTestProgress.setTotalScore(userTestProgress.getTotalScore() + 1);
            } else if (!isCorrectNow && wasCorrectBefore) {
                userTestProgress.getCorrectlyAnsweredQuestionsId().remove(currentQuestion.getId());
                userTestProgress.setTotalScore(userTestProgress.getTotalScore() - 1);
            }
        }

        userTestProgressRepository.save(userTestProgress);
        Question nextQuestion;

        if (nextQuestionId == null) {
            if (isForward) {
                nextQuestion = questionRepository.findFirstByTestIdAndIdGreaterThanOrderById(testId, currentQuestion.getId())
                        .orElseThrow(() -> new NotFoundException("No more questions ahead"));
            } else {
                nextQuestion = questionRepository.findFirstByTestIdAndIdLessThanOrderByIdDesc(testId, currentQuestion.getId())
                        .orElseThrow(() -> new NotFoundException("No previous questions available"));
            }
        } else {
            nextQuestion = questionRepository.findByIdAndTestId(nextQuestionId, testId)
                    .orElseThrow(() -> new NotFoundException("No Question Found"));
        }

        userTestProgress.setCurrentQuestionId(nextQuestion.getId());
        userTestProgressRepository.save(userTestProgress);

        Integer previouslySelectedOption = userTestProgress.getSelectedAnswers().get(nextQuestion.getId());

        int currentQuestionIndex = questionRepository.countByTestIdAndIdLessThan(testId, nextQuestion.getId()) + 1;
        int totalQuestions = userTestProgress.getTotalNumberOfQuestions();


        QuestionResponse nextQuestionResponse = entityMapperUtil.convertToQuestionResponse(nextQuestion, previouslySelectedOption,currentQuestionIndex, totalQuestions, userTestProgress.getLessonIndex(), userTestProgress.getLessonName(), userTestProgress.getChapterIndex());

        return responseUtil.successResponse(nextQuestionResponse);
    }

    @Transactional
    @Override
    public ResponseEntity<ResponseDTO<TestSubmitResponse>> submitTest(HttpServletRequest request, Long testId, boolean isTimeOut) {
        User user = jwtUtils.getUserDataFromRequest(request);
        UserTestProgress userTestProgress = userTestProgressRepository.findByUserIdAndTestId(user.getId(), testId)
                .orElseThrow(() -> new NotFoundException("User test progress not found"));

        int totalQuestions = userTestProgress.getTotalNumberOfQuestions();
        int totalCorrectAnswers = userTestProgress.getCorrectlyAnsweredQuestionsId().size();
        int totalAttemptedQuestions = userTestProgress.getSelectedAnswers().size();
        int securedMarksInPercentage = (int) Math.round((double) totalCorrectAnswers / totalQuestions * 100);


        String remarksComment;
        String remarkSubComment;

        if (isTimeOut) {
            remarksComment = "Oooops";
            remarkSubComment = "You ran out of time.\nYour test has been submitted by default.";
        } else if (securedMarksInPercentage >= 90) {
            remarksComment = "Excellent";
            remarkSubComment = "Outstanding performance!";
        } else if (securedMarksInPercentage >= 75) {
            remarksComment = "Good";
            remarkSubComment = "Keep up the good work!";
        } else if (securedMarksInPercentage >= 50) {
            remarksComment = "Average";
            remarkSubComment = "You can do better!";
        } else {
            remarksComment = "Poor";
            remarkSubComment = "Needs significant improvement.";
        }

        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new NotFoundException("Test not found"));

        UserTestResult userTestResult = userTestResultRepository.findByUser(user)
                .orElseGet(() -> {
                    UserTestResult newResult = new UserTestResult();
                    newResult.setUser(user);
                    newResult.setAverageScore(0.0);
                    newResult.setHighestScore(0.0);
                    return userTestResultRepository.save(newResult);
                });

        UserTestScore userTestScore = new UserTestScore();
        userTestScore.setUserTestResult(userTestResult);
        userTestScore.setTest(test);
        userTestScore.setTotalCorrectAnswers(totalCorrectAnswers);
        userTestScore.setTotalAnsweredQuestions(totalAttemptedQuestions);
        userTestScore.setTotalMarks(securedMarksInPercentage);
        userTestScore.setTotalNumberOfQuestion(totalQuestions);
        userTestScore.setUser(user);
        userTestScoreRepository.save(userTestScore);

        List<UserTestScore> scores = userTestResult.getUserTestScores();
        double totalScore = scores.stream().mapToDouble(UserTestScore::getTotalMarks).sum() + securedMarksInPercentage;
        int scoreCount = scores.size() + 1;
        double newAverageScore = totalScore / scoreCount;
        double newHighestScore = Math.max(userTestResult.getHighestScore(), securedMarksInPercentage);

        userTestResult.setAverageScore(newAverageScore);
        userTestResult.setHighestScore(newHighestScore);
        userTestResultRepository.save(userTestResult);

        TestSubmitResponse testSubmitResponse = new TestSubmitResponse(
                securedMarksInPercentage,
                totalAttemptedQuestions,
                totalQuestions,
                remarksComment,
                remarkSubComment
        );

        userTestProgressRepository.delete(userTestProgress);

        return responseUtil.successResponse(testSubmitResponse);
    }

    @Override
    public ResponseEntity<ResponseDTO<List<QuestionSetResponse>>> getQuestionSet(HttpServletRequest request, Long testId) {
        User user = jwtUtils.getUserDataFromRequest(request);

        UserTestProgress userTestProgress = userTestProgressRepository.findByUserIdAndTestId(user.getId(), testId)
                .orElseThrow(() -> new NotFoundException("User test progress not found for this test"));

        List<Question> questions = questionRepository.findByTestIdOrderById(testId);

        if (questions.isEmpty()) {
            throw new NotFoundException("No questions found for the test with ID: " + testId);
        }

        List<QuestionSetResponse> questionSetResponses = entityMapperUtil.convertToQuestionSetResponse(questions, userTestProgress);

        return responseUtil.successResponse(questionSetResponses);
    }
}



