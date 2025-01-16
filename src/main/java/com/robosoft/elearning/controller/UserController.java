package com.robosoft.elearning.controller;

import com.robosoft.elearning.dto.request.RefreshTokenRequest;
import com.robosoft.elearning.dto.request.UserLoginRequest;
import com.robosoft.elearning.dto.request.UserRequest;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.UserLoginResponse;
import com.robosoft.elearning.dto.response.UserRegisterResponse;
import com.robosoft.elearning.services.OtpServices;
import com.robosoft.elearning.services.UserServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private OtpServices otpServices;

    @Value("${mail.registration.subject}")
    private String mailRegSubject;

    @Value("${mail.registration.content}")
    private String mailRegContent;

    @Autowired
    private UserServices userServices;

    @PostMapping("/v1/send-reg-otp")
    public ResponseEntity<ResponseDTO<Object>> sendOtpForRegistration(@Valid @RequestBody UserRequest userRequest) {
        return otpServices.sendOtp(userRequest.getEmail(), mailRegSubject, mailRegContent);
    }

    @PostMapping("/v1/register")
    public ResponseEntity<ResponseDTO<UserRegisterResponse>> registerUser(@Valid @RequestBody UserRequest userRegister, @RequestParam String otp) {
        return userServices.registerUser(userRegister, otp);
    }

    @PostMapping("/v1/login")
    public ResponseEntity<ResponseDTO<UserLoginResponse>> loginUser(@Valid @RequestBody UserLoginRequest userLoginRequest){
        System.out.println(userLoginRequest);
        return userServices.loginUser(userLoginRequest);
    }

    @PostMapping("/v1/refresh-Token")
    public ResponseEntity<ResponseDTO<UserLoginResponse>> generateAccessTokenFromRefreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return userServices.generateAccessTokenFromRefreshToken(refreshTokenRequest);
    }

    @PostMapping("/v1/logout")
    public String logout(HttpServletRequest request, @RequestBody RefreshTokenRequest refreshTokenRequest){
        return userServices.logout(request,refreshTokenRequest);
    }

    @GetMapping("/v1/test")
    public String test(){
        return "Working";
    }
}
