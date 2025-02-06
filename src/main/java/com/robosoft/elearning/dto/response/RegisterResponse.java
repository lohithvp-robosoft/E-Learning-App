package com.robosoft.elearning.dto.response;

import com.robosoft.elearning.model.Role;
import com.robosoft.elearning.model.User;

import java.util.List;

public class RegisterResponse {
    private String email;
    private String userName;
    private List<Role> roles;

    public RegisterResponse(User user) {
        this.email = user.getEmail();
        this.userName = user.getUserName();
        this.roles = user.getRoles();
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
}
