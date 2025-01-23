package com.robosoft.elearning.dto.response;

import java.time.LocalDateTime;

public class UserTestScoreResponse {
    private Long id;
    private String subjectName;
    private Integer lessonIndex;
    private String lessonName;
    private Integer correctAnsweredQuestion;
    private Integer attemptedQuestion;
    private Integer totalQuestion;
    private Integer securedMarks;
    private LocalDateTime createdAt;

    public UserTestScoreResponse(Long id, String subjectName, Integer lessonIndex, String lessonName, Integer correctAnsweredQuestion, Integer attemptedQuestion, Integer totalQuestion, Integer securedMarks, LocalDateTime createdAt) {
        this.id = id;
        this.subjectName = subjectName;
        this.lessonIndex = lessonIndex;
        this.lessonName = lessonName;
        this.correctAnsweredQuestion = correctAnsweredQuestion;
        this.attemptedQuestion = attemptedQuestion;
        this.totalQuestion = totalQuestion;
        this.securedMarks = securedMarks;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getLessonIndex() {
        return lessonIndex;
    }

    public void setLessonIndex(Integer lessonIndex) {
        this.lessonIndex = lessonIndex;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public Integer getCorrectAnsweredQuestion() {
        return correctAnsweredQuestion;
    }

    public void setCorrectAnsweredQuestion(Integer correctAnsweredQuestion) {
        this.correctAnsweredQuestion = correctAnsweredQuestion;
    }

    public Integer getAttemptedQuestion() {
        return attemptedQuestion;
    }

    public void setAttemptedQuestion(Integer attemptedQuestion) {
        this.attemptedQuestion = attemptedQuestion;
    }

    public Integer getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(Integer totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public Integer getSecuredMarks() {
        return securedMarks;
    }

    public void setSecuredMarks(Integer securedMarks) {
        this.securedMarks = securedMarks;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
