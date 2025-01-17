package com.robosoft.elearning.dto.request;

public class UpdateUserRequest {
    private String userName;
    private String email;

    public UpdateUserRequest(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }
}
