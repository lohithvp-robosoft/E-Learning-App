package com.robosoft.elearning.dto.response;

public class QuestionSetResponse {
    private Long id;
    private String questionStatement;
    private boolean isAttempted;

    public QuestionSetResponse(Long id, String questionStatement, boolean isAttempted) {
        this.id = id;
        this.questionStatement = questionStatement;
        this.isAttempted = isAttempted;
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

    public boolean isAttempted() {
        return isAttempted;
    }

    public void setAttempted(boolean attempted) {
        isAttempted = attempted;
    }
}
