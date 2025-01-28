package com.robosoft.elearning.dto.response;

import java.util.List;

public class QuestionResponse {
    private Long id;
    private String questionStatement;
    private List<String> options;
    private String questionImageUrl;

    public QuestionResponse(Long id, String questionStatement, List<String> options, String questionImageUrl) {
        this.id = id;
        this.questionStatement = questionStatement;
        this.options = options;
        this.questionImageUrl = questionImageUrl;
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

}
