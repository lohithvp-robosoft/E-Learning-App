package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.request.LoginRequest;
import com.robosoft.elearning.dto.request.RefreshTokenRequest;
import com.robosoft.elearning.dto.request.UserRegisterRequest;
import com.robosoft.elearning.dto.response.LoginResponse;
import com.robosoft.elearning.dto.response.RefreshTokenResponse;
import com.robosoft.elearning.dto.response.RegisterResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserServices {

    ResponseEntity<ResponseDTO<RegisterResponse>> registerUser(UserRegisterRequest userRegisterRequest, String otp);

    ResponseEntity<ResponseDTO<LoginResponse>> loginUser(LoginRequest loginRequest);

    ResponseEntity<ResponseDTO<RefreshTokenResponse>> generateAccessTokenFromRefreshToken(RefreshTokenRequest refreshTokenRequest);

    ResponseEntity<ResponseDTO<Void>> logout(HttpServletRequest request, RefreshTokenRequest refreshTokenRequest);
}
