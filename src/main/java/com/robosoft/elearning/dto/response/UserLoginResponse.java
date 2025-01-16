package com.robosoft.elearning.dto.response;

import com.robosoft.elearning.modal.Role;
import com.robosoft.elearning.modal.User;

import java.util.ArrayList;
import java.util.List;

public class UserLoginResponse {
    private Long id;

    private String email;

    private String userName;

    private List<Role> roles = new ArrayList<>();

    private String accessToken;

    private String refreshToken;

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

//    public UserLoginResponse(){}

    public UserLoginResponse(User user, String accessToken, String refreshToken) {
        if (user == null || accessToken == null || accessToken.isEmpty() || refreshToken == null || refreshToken.isEmpty()) {
            throw new IllegalArgumentException("User, access token, and refresh token cannot be null or empty");
        }
        this.id = user.getId();
        this.email = user.getEmail();
        this.userName = user.getUsername();
        this.roles = user.getRoles();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
