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

    public UserLoginResponse(User user, String accessToken, String refreshToken) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.userName = user.getUsername();
        this.roles = user.getRoles();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
