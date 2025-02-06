package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.request.TestRequest;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.TestResponse;
import com.robosoft.elearning.dto.response.TestSubmitResponse;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.model.*;
import com.robosoft.elearning.repository.*;
import com.robosoft.elearning.services.TestServices;
import com.robosoft.elearning.util.EntityMapperUtil;
import com.robosoft.elearning.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TestServicesImpl implements TestServices {


    @Autowired
    private TestRepository testRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private EntityMapperUtil entityMapperUtil;

    @Autowired
    private UserTestProgressRepository userTestProgressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserTestResultRepository userTestResultRepository;

    @Autowired
    private UserTestScoreRepository userTestScoreRepository;

    @Value("${test.error.not-found}")
    private String testNotFoundMessage;

    @Value("${testProgress.error.not-found}")
    private String testProgressNotFoundMessage;

    @Value("${lesson.error.not-found}")
    private String lessonNotFoundMessage;


    @Override
    public ResponseEntity<ResponseDTO<TestResponse>> getOneTest(Long testId) {
        Test test = testRepository.findById(testId).orElseThrow(() -> new NotFoundException(testNotFoundMessage));
        TestResponse testResponse = entityMapperUtil.convertToTestResponse(test);

        return responseUtil.successResponse(testResponse);
    }

    @Override
    public ResponseEntity<ResponseDTO<List<TestResponse>>> getTestsForLesson(Long lessonId) {
        List<Test> tests = testRepository.findByLessonId(lessonId);
        if (tests.isEmpty()) {
            throw new NotFoundException(testNotFoundMessage);
        }
        List<TestResponse> testResponses = tests.stream()
                .map(test -> entityMapperUtil.convertToTestResponse(test))
                .toList();

        return responseUtil.successResponse(testResponses);
    }


    @Override
    public ResponseEntity<ResponseDTO<TestSubmitResponse>> submitTest(Long testId, HttpServletRequest request, boolean isTimeOut) {
        User user = jwtUtils.getUserDataFromRequest(request);
        UserTestProgress userTestProgress = getUserTestProgress(user, testId);
        UserTestResult userTestResult = getOrCreateUserTestResult(user);

        UserTestScore userTestScore = calculateUserTestScore(userTestProgress, userTestResult);
        userTestScoreRepository.save(userTestScore);

        updateUserTestResult(userTestResult, userTestScore);

        TestSubmitResponse testSubmitResponse = createTestSubmitResponse(userTestProgress, userTestScore, isTimeOut);

        userTestProgressRepository.delete(userTestProgress);
        return responseUtil.successResponse(testSubmitResponse);
    }

    private UserTestProgress getUserTestProgress(User user, Long testId) {
        return userTestProgressRepository.findByUserIdAndTestId(user.getId(), testId)
                .orElseThrow(() -> new NotFoundException(testProgressNotFoundMessage));
    }

    private UserTestResult getOrCreateUserTestResult(User user) {
        return userTestResultRepository.findByUser(user).orElseGet(() -> {
            UserTestResult newResult = new UserTestResult();
            newResult.setUser(user);
            newResult.setAverageScore(0.0);
            newResult.setHighestScore(0.0);
            return userTestResultRepository.save(newResult);
        });
    }

    private UserTestScore calculateUserTestScore(UserTestProgress userTestProgress, UserTestResult userTestResult) {
        int totalQuestions = userTestProgress.getTest().getQuestions().size();
        Integer totalCorrectAnswer = userTestProgress.getCorrectlyAnsweredQuestionsId().size();
        Integer totalAttemptedQuestion = userTestProgress.getSelectedAnswers().size();
        Integer securedMarksInPercentage = (int) (((float) totalCorrectAnswer / totalQuestions) * 100);
        UserTestScore userTestScore = new UserTestScore();
        userTestScore.setTest(userTestProgress.getTest());
        userTestScore.setUserTestResult(userTestResult);
        userTestScore.setTotalCorrectAnswers(totalCorrectAnswer);
        userTestScore.setTotalAnsweredQuestions(totalAttemptedQuestion);
        userTestScore.setTotalMarks(securedMarksInPercentage);
        userTestScore.setTotalNumberOfQuestion(totalQuestions);
        userTestScore.setUser(userTestProgress.getUser());
        return userTestScore;
    }

    private void updateUserTestResult(UserTestResult userTestResult, UserTestScore newScore) {
        List<UserTestScore> scores = userTestResult.getUserTestScores();
        double totalScore = scores.stream().mapToDouble(UserTestScore::getTotalMarks).sum() + newScore.getTotalMarks();
        int scoreCount = scores.size() + 1;
        double newAverageScore = totalScore / scoreCount;
        double newHighestScore = Math.max(userTestResult.getHighestScore(), newScore.getTotalMarks());

        userTestResult.setAverageScore(newAverageScore);
        userTestResult.setHighestScore(newHighestScore);
        userTestResultRepository.save(userTestResult);
    }

    private TestSubmitResponse createTestSubmitResponse(UserTestProgress userTestProgress, UserTestScore userTestScore, boolean isTimeOut) {
        int totalQuestions = userTestProgress.getTest().getQuestions().size();
        Integer totalCorrectAnswer = userTestScore.getTotalCorrectAnswers();
        Integer totalAttemptedQuestion = userTestScore.getTotalAnsweredQuestions();
        Integer securedMarksInPercentage = userTestScore.getTotalMarks();

        String remarksComment;
        String remarkSubComment;

        int notCorrect = totalQuestions - totalCorrectAnswer;
        remarkSubComment = "You are " + notCorrect + " correct questions away from 100%. You can do it!";

        if (isTimeOut) {
            remarksComment = "Oooops";
            remarkSubComment = "You ran out of time.\nYour test has been submitted by default.";
        } else if (securedMarksInPercentage == 100) {
            remarksComment = "Excellent";
            remarkSubComment = "Outstanding performance!";
        } else if (securedMarksInPercentage >= 90) {
            remarksComment = "Bravo";
        } else if (securedMarksInPercentage >= 75) {
            remarksComment = "Good";
        } else if (securedMarksInPercentage >= 50) {
            remarksComment = "Average";
        } else {
            remarksComment = "Poor";
        }

        return new TestSubmitResponse(
                securedMarksInPercentage,
                totalAttemptedQuestion,
                totalQuestions,
                remarksComment,
                remarkSubComment
        );
    }

    @Override
    public ResponseEntity<ResponseDTO<TestResponse>> createTest(TestRequest testRequest) {
        Test test = new Test();
        test.setHeading(testRequest.getHeading());
        test.setLevel(testRequest.getLevel());
        test.setTestIcon(testRequest.getTestIcon());
        test.setTotalTime(testRequest.getTotalTime());
        if (testRequest.getLessonId() != null) {
            Lesson lesson = lessonRepository.findById(testRequest.getLessonId())
                    .orElseThrow(() -> new NotFoundException(lessonNotFoundMessage));
            test.setLesson(lesson);
        }
        test.setCreatedAt(LocalDateTime.now());
        test.setUpdatedAt(LocalDateTime.now());
        Test savedTest = testRepository.save(test);

        TestResponse response = convertToDTO(savedTest);
        return responseUtil.successResponse(response, null);
    }

    @Override
    public ResponseEntity<ResponseDTO<TestResponse>> updateTest(Long id, TestRequest testRequest) {
        Test test = testRepository.findById(id).orElseThrow(() -> new NotFoundException(testNotFoundMessage));
        test.setHeading(testRequest.getHeading());
        test.setLevel(testRequest.getLevel());
        test.setTestIcon(testRequest.getTestIcon());
        test.setTotalTime(testRequest.getTotalTime());
        if (testRequest.getLessonId() != null) {
            Lesson lesson = lessonRepository.findById(testRequest.getLessonId())
                    .orElseThrow(() -> new NotFoundException(lessonNotFoundMessage));
            test.setLesson(lesson);
        }
        test.setUpdatedAt(LocalDateTime.now());
        Test updatedTest = testRepository.save(test);

        TestResponse response = convertToDTO(updatedTest);
        return responseUtil.successResponse(response, null);
    }

    @Override
    public ResponseEntity<ResponseDTO<String>> deleteTest(Long id) {
        Test test = testRepository.findById(id).orElseThrow(() -> new NotFoundException(testNotFoundMessage));
        testRepository.delete(test);
        return responseUtil.successResponse(null);
    }

    private TestResponse convertToDTO(Test test) {
        TestResponse dto = new TestResponse();
        dto.setId(test.getId());
        dto.setHeading(test.getHeading());
        dto.setLevel(test.getLevel());
        dto.setTestIcon(test.getTestIcon());
        dto.setTotalTime(test.getTotalTime());
        if (test.getLesson() != null) {
            dto.setLessonId(test.getLesson().getId());
        }
        dto.setCreatedAt(test.getCreatedAt());
        dto.setUpdatedAt(test.getUpdatedAt());
        return dto;
    }
}
