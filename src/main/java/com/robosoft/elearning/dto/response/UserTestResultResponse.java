package com.robosoft.elearning.dto.response;

public class UserTestResultResponse {
    private Long id;
    private Double averageScore;
    private Double highestScore;

    public UserTestResultResponse(Long id, Double averageScore, Double highestScore) {
        this.id = id;
        this.averageScore = averageScore;
        this.highestScore = highestScore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    public Double getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(Double highestScore) {
        this.highestScore = highestScore;
    }
}
