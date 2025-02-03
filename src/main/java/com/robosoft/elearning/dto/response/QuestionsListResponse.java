package com.robosoft.elearning.dto.response;

import java.util.List;

public class QuestionsListResponse {
    private Integer chapterIndex;
    private Integer lessonIndex;
    private String testName;
    private Long testId;
    private Integer totalQuestions;
    private List<QuestionResponse> questions;
    private int duration;

    public QuestionsListResponse(Long testId, Integer chapterIndex, Integer lessonIndex, String testName, Integer totalQuestions, List<QuestionResponse> questions, int duration) {
        this.testId = testId;
        this.chapterIndex = chapterIndex;
        this.lessonIndex = lessonIndex;
        this.testName = testName;
        this.totalQuestions = totalQuestions;
        this.questions = questions;
        this.duration = duration;
    }

    public Integer getChapterIndex() {
        return chapterIndex;
    }

    public void setChapterIndex(Integer chapterIndex) {
        this.chapterIndex = chapterIndex;
    }

    public Integer getLessonIndex() {
        return lessonIndex;
    }

    public void setLessonIndex(Integer lessonIndex) {
        this.lessonIndex = lessonIndex;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public List<QuestionResponse> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionResponse> questions) {
        this.questions = questions;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
