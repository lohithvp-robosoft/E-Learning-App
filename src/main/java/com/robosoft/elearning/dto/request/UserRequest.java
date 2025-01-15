package com.robosoft.elearning.dto.request;

import com.robosoft.elearning.modal.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class UserRequest {
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Username cannot be blank")
    private String userName;

    @Size(min = 6, message = "Password must have at least 6 characters")
    @NotBlank(message = "Password cannot be blank")
    @Pattern(
            regexp = "^(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])(?=.*\\d.*\\d).+$",
            message = "Password must include at least 2 numbers and 1 special character"
    )
    private String password;

    private List<Role> roles = new ArrayList<>();

    public UserRequest(String email, String userName, String password, List<Role> roles) {
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public List<Role> getRoles() {
        return roles;
    }
}
