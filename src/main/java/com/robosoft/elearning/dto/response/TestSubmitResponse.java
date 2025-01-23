package com.robosoft.elearning.dto.response;

public class TestSubmitResponse {
    private String securedMarksInPercentage;
    private int totalNumberOfQuestionsAttempted;
    private int totalNumbersOfQuestions;
    private String remarksComment;
    private String remarkSubComment;

    public TestSubmitResponse(int securedMarksInPercentage, int totalNumberOfQuestionsAttempted, int totalNumbersOfQuestions, String remarksComment, String remarkSubComment) {
        this.securedMarksInPercentage = securedMarksInPercentage+"%";
        this.totalNumberOfQuestionsAttempted = totalNumberOfQuestionsAttempted;
        this.totalNumbersOfQuestions = totalNumbersOfQuestions;
        this.remarksComment = remarksComment;
        this.remarkSubComment = remarkSubComment;
    }

    public String getSecuredMarksInPercentage() {
        return securedMarksInPercentage;
    }

    public void setSecuredMarksInPercentage(String securedMarksInPercentage) {
        this.securedMarksInPercentage = securedMarksInPercentage;
    }

    public int getTotalNumberOfQuestionsAttempted() {
        return totalNumberOfQuestionsAttempted;
    }

    public void setTotalNumberOfQuestionsAttempted(int totalNumberOfQuestionsAttempted) {
        this.totalNumberOfQuestionsAttempted = totalNumberOfQuestionsAttempted;
    }

    public int getTotalNumbersOfQuestions() {
        return totalNumbersOfQuestions;
    }

    public void setTotalNumbersOfQuestions(int totalNumbersOfQuestions) {
        this.totalNumbersOfQuestions = totalNumbersOfQuestions;
    }

    public String getRemarksComment() {
        return remarksComment;
    }

    public void setRemarksComment(String remarksComment) {
        this.remarksComment = remarksComment;
    }

    public String getRemarkSubComment() {
        return remarkSubComment;
    }

    public void setRemarkSubComment(String remarkSubComment) {
        this.remarkSubComment = remarkSubComment;
    }
}
