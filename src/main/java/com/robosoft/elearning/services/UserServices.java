package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.request.*;
import com.robosoft.elearning.dto.response.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface UserServices {

    ResponseEntity<ResponseDTO<RegisterResponse>> register(BaseRegisterRequest baseRegisterRequest, String otp);

    ResponseEntity<ResponseDTO<LoginResponse>> login(LoginRequest loginRequest);

    ResponseEntity<ResponseDTO<RefreshTokenResponse>> generateAccessTokenFromRefreshToken(RefreshTokenRequest refreshTokenRequest);

        ResponseEntity<ResponseDTO<Void>> logout(HttpServletRequest request, RefreshTokenRequest refreshTokenRequest);

    public ResponseEntity<ResponseDTO<UserDetailResponse>> update(UpdateUserRequest updateUserRequest, MultipartFile file, HttpServletRequest request) throws IOException;


    public ResponseEntity<ResponseDTO<Void>> forgotPassword(ForgotPasswordRequest request);

    ResponseEntity<ResponseDTO<Void>> resetPassword(ResetPasswordRequest resetPasswordRequest, HttpServletRequest request);

    ResponseEntity<ResponseDTO<Void>> forgotResetPassword(ResetPasswordRequest resetPasswordRequest, String otp);

    ResponseEntity<ResponseDTO<UserDetailResponse>> getProfile(HttpServletRequest request);


}