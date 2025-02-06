package com.robosoft.elearning.dto.request;

import com.robosoft.elearning.model.Role;

public class UserRegisterRequest extends BaseRegisterRequest {
    public UserRegisterRequest(String email, String userName, String password) {
        super(email, userName, password, Role.USER);
    }

    public UserRegisterRequest() {
        super();
        this.getRoles().add(Role.USER);
    }
}
