package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.request.UserLoginRequest;
import com.robosoft.elearning.dto.request.UserRequest;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.UserLoginResponse;
import com.robosoft.elearning.dto.response.UserRegisterResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserServices {

    ResponseEntity<ResponseDTO<UserRegisterResponse>> registerUser(UserRequest userRequest, String otp);

    ResponseEntity<ResponseDTO<UserLoginResponse>> loginUser(UserLoginRequest userRequest);

    ResponseEntity<ResponseDTO<UserLoginResponse>> generateAccessTokenFromRefreshToken(HttpServletRequest request);
}
