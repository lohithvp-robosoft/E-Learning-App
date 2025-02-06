package com.robosoft.elearning.dto.request;

import com.robosoft.elearning.model.Role;

import java.util.ArrayList;
import java.util.List;

public class AdminRegistrationRequest extends BaseRegisterRequest {
    private List<Role> roles = new ArrayList<>();
    public AdminRegistrationRequest(String email, String userName, String password) {
        super(email, userName, password, Role.USER);
        roles.add(Role.ADMIN);

    }

    public AdminRegistrationRequest() {
        super();
        this.getRoles().add(Role.ADMIN);
    }
}
