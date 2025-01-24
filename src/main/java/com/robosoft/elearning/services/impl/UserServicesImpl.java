package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.request.*;
import com.robosoft.elearning.dto.response.*;
import com.robosoft.elearning.exception.EmailAlreadyExistsException;
import com.robosoft.elearning.exception.InvalidCredentialsException;
import com.robosoft.elearning.exception.JwtException;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.modal.User;
import com.robosoft.elearning.repository.UserRepository;
import com.robosoft.elearning.services.OtpServices;
import com.robosoft.elearning.services.UserServices;
import com.robosoft.elearning.util.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private OtpServices otpServices;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailHandler emailHandler;

    @Autowired
    private ObjectMapperUtil objectMapperUtil;

    @Value("${mail.registration.success.subject}")
    private String mailRegSuccessSubject;

    @Value("${mail.registration.success.content}")
    private String mailRegSuccessContent;

    @Value("${success.logout.message}")
    private String logoutMessage;

    @Value("${success.passwordUpdated.message}")
    private String passwordUpdatedMessage;

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private FileStorageUtil fileStorageUtil;

    @Autowired
    private EntityMapperUtil entityMapperUtil;

    @Value("${email.already.exist.message}")
    private String emailAlreadyExistMessage;

    @Value("${token.blacklistedMessage}")
    private String tokenBlacklistMessage;

    @Value("${mail.changePassword.subject}")
    private String changePasswordMailSubject;

    @Value("${mail.changePassword.content}")
    private String changePasswordMailContent;

    @Value("${message.otpSend.success}")
    private String otpSendSuccessMessage;

    @Override
    public ResponseEntity<ResponseDTO<RegisterResponse>> register(BaseRegisterRequest registerRequest, String otp) {
        boolean isOtpValid = otpServices.validateOtp(registerRequest.getEmail(), otp);
        if (isOtpValid) {
            if (userRepository.existsByEmail(registerRequest.getEmail())) {
                throw new EmailAlreadyExistsException(emailAlreadyExistMessage);
            }
            User newUser = new User(registerRequest, passwordEncoder.encode(registerRequest.getPassword()));

            userRepository.save(newUser);
            RegisterResponse userRegisterResponse = new RegisterResponse(newUser);

            emailHandler.sendMail(registerRequest.getEmail(), mailRegSuccessSubject, "Hi, " + newUser.getUserName() + "\n\n" + mailRegSuccessContent);

            return responseUtil.successResponse(userRegisterResponse);
        } else {
            return responseUtil.errorResponse("Invalid OTP " + otp);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<LoginResponse>> login(LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            throw new InvalidCredentialsException();

        String accessToken = jwtUtils.generateAccessToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(user);

        user.setDeviceToken(loginRequest.getDeviceToken());
        userRepository.save(user);
        return responseUtil.successResponse(new LoginResponse(user, accessToken, refreshToken));
    }

    @Override
    public ResponseEntity<ResponseDTO<RefreshTokenResponse>>
    generateAccessTokenFromRefreshToken(RefreshTokenRequest refreshTokenRequest) {

        if (jwtUtils.isTokenBlacklisted(refreshTokenRequest.getRefreshToken())) {
            throw new JwtException(tokenBlacklistMessage);
        }
        if (jwtUtils.isAccessToken(refreshTokenRequest.getRefreshToken()))
            throw new JwtException("Please use Valid refresh Token");

        String userId = jwtUtils.getUserIdFromJwtToken(refreshTokenRequest.getRefreshToken());
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new JwtException("User not found"));

        return responseUtil.successResponse(new RefreshTokenResponse(user, jwtUtils.generateAccessToken(user)));
    }

    @Override
    public ResponseEntity<ResponseDTO<Void>> logout(HttpServletRequest request, RefreshTokenRequest refreshTokenRequest) {
        User user = jwtUtils.getUserDataFromRequest(request);
        user.setDeviceToken(null);
        userRepository.save(user);
        jwtUtils.blackListAccessToken(jwtUtils.getJwtFromHeader(request));
        jwtUtils.blackListRefreshToken(refreshTokenRequest.getRefreshToken());
        return responseUtil.successResponse(null, logoutMessage);
    }


    @Override
    public ResponseEntity<ResponseDTO<UserDetailResponse>> update(UpdateUserRequest updateUserRequest,
                                                                  MultipartFile file,
                                                                  HttpServletRequest request) throws IOException {
        long userId = jwtUtils.getUserIdFromRequestHeader(request);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        updateUserData(user, updateUserRequest);

        if (file != null && !file.isEmpty()) {
            String imageUrl = handleFileUpload(file, user.getId());
            user.setProfileImageUrl(imageUrl);
        }

        userRepository.save(user);

        UserDetailResponse userDetailResponse = entityMapperUtil.convertUserToUserDetailResponse(user);

        return responseUtil.successResponse(userDetailResponse, "User updated successfully");
    }

    private void updateUserData(User user, UpdateUserRequest updateUserRequest) {
        if (updateUserRequest.getUserName() != null && !updateUserRequest.getUserName().isEmpty()) {
            user.setUserName(updateUserRequest.getUserName());
        }
        if (updateUserRequest.getEmail() != null && !updateUserRequest.getEmail().isEmpty()) {
            user.setEmail(updateUserRequest.getEmail());
        }
    }

    private String handleFileUpload(MultipartFile file, long userId) throws IOException {
        String folder = "user-profile-images";
        try {
            return fileStorageUtil.storeFile(file, folder, userId);
        } catch (Exception e) {
            throw new IOException("Error uploading file", e);
        }
    }


    @Override
    public ResponseEntity<ResponseDTO<Void>> forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new NotFoundException("Email Not Registered")
        );
        otpServices.sendOtp(user.getEmail(), changePasswordMailSubject, changePasswordMailContent);
        return responseUtil.successResponse(null, otpSendSuccessMessage);
    }

    @Override
    public ResponseEntity<ResponseDTO<Void>> resetPassword(ResetPasswordRequest resetPasswordRequest, HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);

        String encodedPassword = passwordEncoder.encode(resetPasswordRequest.getNewPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return responseUtil.successResponse(null, passwordUpdatedMessage);
    }


    @Override
    public ResponseEntity<ResponseDTO<Void>> forgotResetPassword(ResetPasswordRequest resetPasswordRequest, String otp) {
        User user = userRepository.findByEmail(resetPasswordRequest.getEmail()).orElseThrow(()->
                new NotFoundException("User Not Registered"));
        boolean isValidOtp = otpServices.validateOtp(user.getEmail(), otp);
        if (isValidOtp) {
            String encodedPassword = passwordEncoder.encode(resetPasswordRequest.getNewPassword());
            user.setPassword(encodedPassword);
            userRepository.save(user);
            return responseUtil.successResponse(null, passwordUpdatedMessage);
        } else {
            return responseUtil.errorResponse("Invalid OTP " + otp);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<UserDetailResponse>> getProfile(HttpServletRequest request) {
        User user = Optional.ofNullable(jwtUtils.getUserDataFromRequest(request)).orElseThrow(()-> new NotFoundException("User not Found"));
        UserDetailResponse userDetailResponse = entityMapperUtil.convertUserToUserDetailResponse(user);
        return responseUtil.successResponse(userDetailResponse);
    }

    @Override
    public ResponseEntity<ResponseDTO<Void>> toggleNotification(HttpServletRequest request) {
        return null;
    }

}
