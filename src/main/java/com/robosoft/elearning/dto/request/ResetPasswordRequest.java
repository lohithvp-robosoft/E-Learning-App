package com.robosoft.elearning.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ResetPasswordRequest {

    @Size(min = 6, message = "Password must have at least 6 characters")
    @NotBlank(message = "Password cannot be blank")
    @Pattern(
            regexp = "^(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])(?=.*\\d.*\\d).+$",
            message = "Password must include at least 2 numbers and 1 special character"
    )
    private String newPassword;

    public ResetPasswordRequest(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
