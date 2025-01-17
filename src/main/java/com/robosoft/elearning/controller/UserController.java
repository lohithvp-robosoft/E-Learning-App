package com.robosoft.elearning.controller;

import com.robosoft.elearning.dto.request.*;
import com.robosoft.elearning.dto.response.*;
import com.robosoft.elearning.services.OtpServices;
import com.robosoft.elearning.services.UserServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private OtpServices otpServices;

    @Value("${mail.registration.subject}")
    private String mailRegSubject;

    @Value("${mail.registration.content}")
    private String mailRegContent;

    @Autowired
    private UserServices userServices;

    @PostMapping("/v1/user/send-reg-otp")
    public ResponseEntity<ResponseDTO<Void>> sendOtpForRegistrationForUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        return otpServices.sendOtp(userRegisterRequest.getEmail(), mailRegSubject, mailRegContent);
    }

    @PostMapping("/v1/admin/send-reg-otp")
    public ResponseEntity<ResponseDTO<Void>> sendOtpForRegistrationForAdmin(@Valid @RequestBody AdminRegistrationRequest adminRegistrationRequest) {
        return otpServices.sendOtp(adminRegistrationRequest.getEmail(), mailRegSubject, mailRegContent);
    }

    @PostMapping("/v1/user/register")
    public ResponseEntity<ResponseDTO<RegisterResponse>> registerUser(@Valid @RequestBody UserRegisterRequest userRegister, @RequestParam String otp) {
        return userServices.register(userRegister, otp);
    }

    @PostMapping("/v1/admin/register")
    public ResponseEntity<ResponseDTO<RegisterResponse>> registerAdmin(@Valid @RequestBody AdminRegistrationRequest adminRegister, @RequestParam String otp) {
        return userServices.register(adminRegister, otp);
    }

    @PostMapping("/v1/login")
    public ResponseEntity<ResponseDTO<LoginResponse>> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        return userServices.login(loginRequest);
    }

    @PostMapping("/v1/refresh-Token")
    public ResponseEntity<ResponseDTO<RefreshTokenResponse>> generateAccessTokenFromRefreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return userServices.generateAccessTokenFromRefreshToken(refreshTokenRequest);
    }

    @PostMapping("/v1/logout")
    public ResponseEntity<ResponseDTO<Void>> logout(HttpServletRequest request, @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return userServices.logout(request, refreshTokenRequest);
    }

    @PostMapping("/v1/profile-update")
    public ResponseEntity<ResponseDTO<UserDetailResponse>> update(@ModelAttribute UpdateUserRequest userRequest, @RequestParam(required = false) MultipartFile file, HttpServletRequest request) throws IOException {
        return userServices.update(userRequest, file, request);
    }

    @PostMapping("/v1/forgot-password")
    public ResponseEntity<ResponseDTO<Void>> forgotPassword(HttpServletRequest request) {
        return userServices.forgotPassword(request);
    }

    @GetMapping("/v1/reset-password")
    public ResponseEntity<ResponseDTO<Void>> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest, @RequestParam String otp, HttpServletRequest request) {
        return userServices.resetPassword(resetPasswordRequest,otp,request);
    }
}
