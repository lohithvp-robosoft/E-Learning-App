package com.robosoft.elearning.dto.response;

import java.util.List;

public class QuestionResponse {
    private Long id;
    private String questionStatement;
    private List<String> options;
    private String questionImageUrl;
//    private Integer previouslySelectedOption;
//    private Integer currentQuestionIndex;
//    private Integer totalQuestions;
//    private Integer lessonIndex;
//    private String lessonName;
//    private Integer chapterIndex;

    public QuestionResponse(Long id, String questionStatement, List<String> options, String questionImageUrl) {
        this.id = id;
        this.questionStatement = questionStatement;
        this.options = options;
        this.questionImageUrl = questionImageUrl;
//        this.previouslySelectedOption = previouslySelectedOption;
//        this.currentQuestionIndex = currentQuestionIndex;
//        this.totalQuestions = totalQuestions;
//        this.lessonIndex = lessonIndex;
//        this.lessonName = lessonName;
//        this.chapterIndex = chapterIndex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionStatement() {
        return questionStatement;
    }

    public void setQuestionStatement(String questionStatement) {
        this.questionStatement = questionStatement;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getQuestionImageUrl() {
        return questionImageUrl;
    }

    public void setQuestionImageUrl(String questionImageUrl) {
        this.questionImageUrl = questionImageUrl;
    }

//    public Integer getPreviouslySelectedOption() {
//        return previouslySelectedOption;
//    }
//
//    public void setPreviouslySelectedOption(Integer previouslySelectedOption) {
//        this.previouslySelectedOption = previouslySelectedOption;
//    }
//
//    public Integer getCurrentQuestionIndex() {
//        return currentQuestionIndex;
//    }
//
//    public void setCurrentQuestionIndex(Integer currentQuestionIndex) {
//        currentQuestionIndex = currentQuestionIndex;
//    }
//
//    public Integer getTotalQuestions() {
//        return totalQuestions;
//    }
//
//    public void setTotalQuestions(Integer totalQuestions) {
//        totalQuestions = totalQuestions;
//    }
//
//    public Integer getLessonIndex() {
//        return lessonIndex;
//    }
//
//    public void setLessonIndex(Integer lessonIndex) {
//        this.lessonIndex = lessonIndex;
//    }
//
//    public String getLessonName() {
//        return lessonName;
//    }
//
//    public void setLessonName(String lessonName) {
//        this.lessonName = lessonName;
//    }
//
//    public Integer getChapterIndex() {
//        return chapterIndex;
//    }
//
//    public void setChapterIndex(Integer chapterIndex) {
//        this.chapterIndex = chapterIndex;
//    }
}
