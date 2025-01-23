package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.controller.TestController;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.TestResponse;
import com.robosoft.elearning.dto.response.TestSubmitResponse;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.modal.*;
import com.robosoft.elearning.repository.*;
import com.robosoft.elearning.services.TestServices;
import com.robosoft.elearning.util.EntityMapperUtil;
import com.robosoft.elearning.util.ObjectMapperUtil;
import com.robosoft.elearning.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class TestServicesImpl implements TestServices {


    @Autowired
    private TestRepository testRepository;

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


    @Override
    public ResponseEntity<ResponseDTO<TestResponse>> getOneTest(Long testId) {
        Test test = testRepository.findById(testId).orElseThrow(() -> new NotFoundException("Test Not Found"));
        TestResponse testResponse = entityMapperUtil.convertToTestResponse(test);

        return responseUtil.successResponse(testResponse);
    }

    @Override
    public ResponseEntity<ResponseDTO<List<TestResponse>>> getTestsForLesson(Long lessonId) {
        List<Test> tests = testRepository.findByLessonId(lessonId);
        if(tests.isEmpty()){
            throw  new NotFoundException("No Test Available");
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
                .orElseThrow(() -> new NotFoundException("User Test Progress Not Found"));
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


//    @Override
//    public ResponseEntity<ResponseDTO<TestSubmitResponse>> submitTest(Long testId, HttpServletRequest request, boolean isTimeOut) {
//        User user = jwtUtils.getUserDataFromRequest(request);
//        UserTestProgress userTestProgress = userTestProgressRepository.findByUserIdAndTestId(user.getId(),testId)
//                .orElseThrow(() -> new NotFoundException("User Test Progress Not Found"));
//        UserTestResult userTestResult = userTestResultRepository.findByUser(user)
//                .orElseGet(() -> {
//                    UserTestResult newResult = new UserTestResult();
//                    newResult.setUser(user);
//                    newResult.setAverageScore(0.0);
//                    newResult.setHighestScore(0.0);
//                    return userTestResultRepository.save(newResult);
//                });
//        UserTestScore userTestScore = new UserTestScore();
//        int totalQuestions = userTestProgress.getTest().getQuestions().size();
//        Integer totalCorrectAnswer = userTestProgress.getCorrectlyAnsweredQuestionsId().size();
//        Integer totalAttemptedQuestion = userTestProgress.getSelectedAnswers().size();
//        Integer securedMarksInPercentage = (int) (((float) totalCorrectAnswer / totalQuestions) * 100);
//        userTestScore.setTest(userTestProgress.getTest());
//        userTestScore.setUserTestResult(userTestResult);
//        userTestScore.setTotalCorrectAnswers(totalCorrectAnswer);
//        userTestScore.setTotalAnsweredQuestions(totalAttemptedQuestion);
//        userTestScore.setTotalMarks(securedMarksInPercentage);
//
//        userTestScoreRepository.save(userTestScore);
//        List<UserTestScore> scores = userTestResult.getUserTestScores();
//        double totalScore = scores.stream().mapToDouble(UserTestScore::getTotalMarks).sum() + securedMarksInPercentage;
//        int scoreCount = scores.size() + 1;
//        double newAverageScore = totalScore / scoreCount;
//        double newHighestScore = Math.max(userTestResult.getHighestScore(), securedMarksInPercentage);
//        userTestResult.setAverageScore(newAverageScore);
//        userTestResult.setHighestScore(newHighestScore);
//        userTestResultRepository.save(userTestResult);
//
//        String remarksComment;
//        int notCorrect = totalQuestions - totalCorrectAnswer;
//        String remarkSubComment = "You are "+ notCorrect +" correct questions away from 100%, You can do it" ;
//        if (isTimeOut) {
//            remarksComment = "Oooops";
//            remarkSubComment = "You ran out of time.\nYour test has been submitted by default.";
//        } else if (securedMarksInPercentage == 100) {
//            remarksComment = "Excellent";
//            remarkSubComment = "Outstanding performance!";
//        }else if (securedMarksInPercentage >= 90) {
//            remarksComment = "Bravo";
//        }
//        else if (securedMarksInPercentage >= 75) {
//            remarksComment = "Good";
//        } else if (securedMarksInPercentage >= 50) {
//            remarksComment = "Average";
//        } else {
//            remarksComment = "Poor";
//        }
//
//        TestSubmitResponse testSubmitResponse = new TestSubmitResponse(
//                securedMarksInPercentage,
//                totalAttemptedQuestion,
//                totalQuestions,
//                remarksComment,
//                remarkSubComment
//        );
//
//        userTestProgressRepository.delete(userTestProgress);
//        return responseUtil.successResponse(testSubmitResponse);
//    }
}
