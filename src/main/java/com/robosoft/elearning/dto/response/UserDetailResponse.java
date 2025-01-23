package com.robosoft.elearning.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDetailResponse {
    private long id;
    @JsonProperty("profileImageUrl")
    private String profileImageUrl;
    @JsonProperty("userName")
    private String userName;
    private String email;
    private double completerCompletedInPercentage;
    private double AverageTestScore;
    private double highestTestScore;
    private boolean isNotificationEnabled;

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getCompleterCompletedInPercentage() {
        return completerCompletedInPercentage;
    }

    public void setCompleterCompletedInPercentage(double completerCompletedInPercentage) {
        this.completerCompletedInPercentage = completerCompletedInPercentage;
    }

    public double getAverageTestScore() {
        return AverageTestScore;
    }

    public void setAverageTestScore(double averageTestScore) {
        AverageTestScore = averageTestScore;
    }

    public double getHighestTestScore() {
        return highestTestScore;
    }

    public void setHighestTestScore(double highestTestScore) {
        this.highestTestScore = highestTestScore;
    }

    public boolean isNotificationEnabled() {
        return isNotificationEnabled;
    }

    public void setNotificationEnabled(boolean notificationEnabled) {
        isNotificationEnabled = notificationEnabled;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
