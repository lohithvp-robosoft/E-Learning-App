package com.robosoft.elearning.controller;


import com.robosoft.elearning.dto.request.UserRequest;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.UserRegisterResponse;
import com.robosoft.elearning.services.OtpServices;
import com.robosoft.elearning.services.UserServices;
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

//    @Autowired
//    private UserRequest userRequest;

    @PostMapping("/v1/send-reg-otp")
    public ResponseEntity<ResponseDTO<Object>> sendOtpForRegistration(@Valid @RequestBody UserRequest userRequest) {
        return otpServices.sendOtp(userRequest.getEmail(), mailRegSubject, mailRegContent);
    }

    @PostMapping("/v1/register")
    public ResponseEntity<ResponseDTO<UserRegisterResponse>> registerUser(@Valid @RequestBody UserRequest userRegister, @RequestParam String otp) {
        return userServices.registerUser(userRegister, otp);
    }
}
