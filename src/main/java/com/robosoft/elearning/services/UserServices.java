package com.robosoft.elearning.services;

//import com.robosoft.elearning.dto.request.LogoutRequest;
import com.robosoft.elearning.dto.request.RefreshTokenRequest;
import com.robosoft.elearning.dto.request.UserLoginRequest;
import com.robosoft.elearning.dto.request.UserRequest;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.UserLoginResponse;
import com.robosoft.elearning.dto.response.UserRegisterResponse;
import com.robosoft.elearning.modal.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserServices {

    ResponseEntity<ResponseDTO<UserRegisterResponse>> registerUser(UserRequest userRequest, String otp);

    ResponseEntity<ResponseDTO<UserLoginResponse>> loginUser(UserLoginRequest userLoginRequest);

//    User loginUser(UserLoginRequest userLoginRequest);

    ResponseEntity<ResponseDTO<UserLoginResponse>> generateAccessTokenFromRefreshToken(RefreshTokenRequest refreshTokenRequest);

    String logout(HttpServletRequest request, RefreshTokenRequest refreshTokenRequest);
}
