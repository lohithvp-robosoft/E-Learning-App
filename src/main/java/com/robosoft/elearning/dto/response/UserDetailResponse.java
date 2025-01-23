package com.robosoft.elearning.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.robosoft.elearning.modal.UserTestResult;

public class UserDetailResponse {
    private long id;
    @JsonProperty("profileImageUrl")
    private String profileImageUrl;
    @JsonProperty("userName")
    private String userName;
    private String email;
    private int completerChapterInPercentage;

    private UserTestResultResponse testResult;

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

    public int getCompleterChapterInPercentage() {
        return completerChapterInPercentage;
    }

    public void setCompleterChapterInPercentage(int completerChapterInPercentage) {
        this.completerChapterInPercentage = completerChapterInPercentage;
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

    public UserTestResultResponse getTestResult() {
        return testResult;
    }

    public void setTestResult(UserTestResultResponse testResult) {
        this.testResult = testResult;
    }

}
