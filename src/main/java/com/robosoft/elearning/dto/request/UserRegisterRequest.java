package com.robosoft.elearning.dto.request;

import com.robosoft.elearning.modal.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

public class UserRegisterRequest extends BaseRegisterRequest {
    public UserRegisterRequest(String email, String userName, String password) {
        super(email, userName, password, Role.USER);
    }

    public UserRegisterRequest() {
        super();
        this.getRoles().add(Role.USER);
    }
}
