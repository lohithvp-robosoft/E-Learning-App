package com.robosoft.elearning.dto.response;

import com.robosoft.elearning.model.Role;
import com.robosoft.elearning.model.User;

import java.util.ArrayList;
import java.util.List;

public class RefreshTokenResponse {
    private Long id;

    private String email;

    private String userName;

    private List<Role> roles = new ArrayList<>();

    private String accessToken;

    public RefreshTokenResponse(User user, String accessToken) {
        if (user == null ||  accessToken == null || accessToken.isEmpty()) {
            throw new IllegalArgumentException("User, access token, and refresh token cannot be null or empty");
        }
        this.id = user.getId();
        this.email = user.getEmail();
        this.userName = user.getUserName();
        this.roles = user.getRoles();
        this.accessToken = accessToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
