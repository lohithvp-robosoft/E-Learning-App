package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.request.LoginRequest;
import com.robosoft.elearning.dto.request.RefreshTokenRequest;
import com.robosoft.elearning.dto.request.UserRegisterRequest;
import com.robosoft.elearning.dto.response.RefreshTokenResponse;
import com.robosoft.elearning.dto.response.RegisterResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.LoginResponse;
import com.robosoft.elearning.exception.EmailAlreadyExistsException;
import com.robosoft.elearning.exception.InvalidCredentialsException;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.modal.User;
import com.robosoft.elearning.repository.UserRepository;
import com.robosoft.elearning.services.OtpServices;
import com.robosoft.elearning.services.UserServices;
import com.robosoft.elearning.util.EmailHandler;
import com.robosoft.elearning.util.ResponseUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServicesImpl implements UserServices {

    private static final Log log = LogFactory.getLog(UserServicesImpl.class);
    @Autowired
    private OtpServices otpServices;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailHandler emailHandler;

    @Value("${mail.registration.success.subject}")
    private String mailRegSuccessSubject;

    @Value("${mail.registration.success.content}")
    private String mailRegSuccessContent;

    @Value("${success.logout.message}")
    private String logoutMessage;

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${email.already.exist.message}")
    private String emailAlreadyExistMessage;

    @Value("${token.blacklistedMessage}")
    private String tokenBlacklistMessage;

    @Override
    public ResponseEntity<ResponseDTO<RegisterResponse>> registerUser(UserRegisterRequest userRegisterRequest, String otp) {
        boolean isOtpValid = otpServices.validateOtp(userRegisterRequest.getEmail(), otp);
        if (isOtpValid) {
            if (userRepository.existsByEmail(userRegisterRequest.getEmail())) {
                throw new EmailAlreadyExistsException(emailAlreadyExistMessage);
            }
            User newUser = new User(userRegisterRequest);
             RegisterResponse userRegisterResponse = new RegisterResponse(newUser);

            emailHandler.sendMail(userRegisterRequest.getEmail(), mailRegSuccessSubject, "Hi, "+newUser.getUsername()+"\n\n"+mailRegSuccessContent);

            return responseUtil.successResponse(userRegisterResponse);
        } else {
            return responseUtil.errorResponse("Invalid OTP " + otp);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<LoginResponse>> loginUser(LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            throw new InvalidCredentialsException();

        String accessToken = jwtUtils.generateAccessToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(user);

        return responseUtil.successResponse(new LoginResponse(user, accessToken,refreshToken));
    }

    @Override
    public ResponseEntity<ResponseDTO<RefreshTokenResponse>> generateAccessTokenFromRefreshToken(RefreshTokenRequest refreshTokenRequest) {

        if (jwtUtils.isTokenBlacklisted(refreshTokenRequest.getRefreshToken())) {
            throw new JwtException(tokenBlacklistMessage);
        }

        String userId = jwtUtils.getUserIdFromJwtToken(refreshTokenRequest.getRefreshToken());
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new JwtException("User not found"));

        return responseUtil.successResponse(new RefreshTokenResponse(user,jwtUtils.generateAccessToken(user)));
    }

    @Override
    public ResponseEntity<ResponseDTO<Void>> logout(HttpServletRequest request, RefreshTokenRequest refreshTokenRequest) {
        jwtUtils.blackListAccessToken(jwtUtils.getJwtFromHeader(request));
        jwtUtils.blackListRefreshToke(refreshTokenRequest.getRefreshToken());
        return responseUtil.successResponse(null,logoutMessage);
    }
}
