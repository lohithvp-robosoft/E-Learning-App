package com.robosoft.elearning.util;

import com.robosoft.elearning.dto.response.*;
import com.robosoft.elearning.modal.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntityMapperUtil {

    public UserDetailResponse convertUserToUserDetailResponse(User user) {
        if (user == null) {
            return null;
        }

        UserDetailResponse userDetailResponse = new UserDetailResponse();
        userDetailResponse.setId(user.getId());
        userDetailResponse.setEmail(user.getEmail());
        userDetailResponse.setUserName(user.getUserName());
        userDetailResponse.setProfileImageUrl(user.getProfileImageUrl());
        UserTestResult testResult = user.getUserTestResult();
        UserTestResultResponse testResultResponse = new UserTestResultResponse(
                testResult.getId(),
                testResult.getAverageScore(),
                testResult.getHighestScore()
        );
        userDetailResponse.setTestResult(testResultResponse);

        return userDetailResponse;
    }

    public TestResponse convertToTestResponse(Test test) {
        return new TestResponse(
                test.getId(),
                test.getTestIcon(),
                test.getHeading(),
                test.getLevel(),
                test.getTotalTime(),
                test.getLesson() != null ? test.getLesson().getLessonName() : null,
                test.getQuestions().size(),
                "You have "+ test.getTotalTime()+" minutes to answer all "+test.getQuestions().size()+" questions.",
                test.getCreatedAt(),
                test.getUpdatedAt()
        );
    }

    public QuestionResponse convertToQuestionResponse(Question question, Integer previouslySelectedOption, Integer currentQuestionIndex, Integer totalNoOfQuestion, Integer lessonIndex, String lessonName, Integer chapterIndex) {
        if (question == null) {
            return null;
        }

        return new QuestionResponse(
                question.getId(),
                question.getQuestionStatement(),
                question.getOptions(),
                question.getQuestionImageUrl(),
                previouslySelectedOption,
                currentQuestionIndex,
                totalNoOfQuestion,
                lessonIndex,
                lessonName,
                chapterIndex
        );
    }

    public List<QuestionSetResponse> convertToQuestionSetResponse(List<Question> questions, UserTestProgress userTestProgress) {
        if (questions == null || userTestProgress == null) {
            return List.of();
        }

        return questions.stream()
                .map(question -> new QuestionSetResponse(
                        question.getId(),
                        question.getQuestionStatement(),

                        userTestProgress.getSelectedAnswers().containsKey(question.getId())
                ))
                .toList();
    }

    public TestSubmitResponse convertToTestSubmitResponse(int securedMarksInPercentage, int totalAttemptedQuestions, int totalQuestions) {
        String remarksComment;
        String remarkSubComment;

        if (totalAttemptedQuestions < totalQuestions) {
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

        return new TestSubmitResponse(
                securedMarksInPercentage,
                totalAttemptedQuestions,
                totalQuestions,
                remarksComment,
                remarkSubComment
        );
    }

}
