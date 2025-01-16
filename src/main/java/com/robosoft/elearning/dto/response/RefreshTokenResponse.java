package com.robosoft.elearning.dto.response;

import com.robosoft.elearning.modal.Role;
import com.robosoft.elearning.modal.User;

import java.util.ArrayList;
import java.util.List;

public class RefreshTokenResponse {
    private Long id;

    private String email;

    private String userName;

    private List<Role> roles = new ArrayList<>();

    private String refreshToken;

    public RefreshTokenResponse(User user, String refreshToken) {
        if (user == null ||  refreshToken == null || refreshToken.isEmpty()) {
            throw new IllegalArgumentException("User, access token, and refresh token cannot be null or empty");
        }
        this.id = user.getId();
        this.email = user.getEmail();
        this.userName = user.getUsername();
        this.roles = user.getRoles();
        this.refreshToken = refreshToken;
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

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
