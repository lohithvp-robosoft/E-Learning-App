package com.robosoft.elearning.services.impl;

//import com.robosoft.elearning.dto.request.LogoutRequest;
import com.robosoft.elearning.dto.request.RefreshTokenRequest;
import com.robosoft.elearning.dto.request.UserLoginRequest;
import com.robosoft.elearning.dto.request.UserRequest;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.UserLoginResponse;
import com.robosoft.elearning.dto.response.UserRegisterResponse;
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

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public ResponseEntity<ResponseDTO<UserRegisterResponse>> registerUser(UserRequest userRequest, String otp) {
        boolean isOtpValid = otpServices.validateOtp(userRequest.getEmail(), otp);
        if (isOtpValid) {
            if (userRepository.existsByEmail(userRequest.getEmail())) {
                throw new EmailAlreadyExistsException("Email is already taken. Please choose another one.");
            }
            User newUser = new User(userRequest.getEmail(), userRequest.getUserName(), passwordEncoder.encode(userRequest.getPassword()), userRequest.getRoles());
//            log.info("User Role : {}", newUser.getRoles());
            userRepository.save(newUser);
            UserRegisterResponse userRegisterResponse = new UserRegisterResponse(newUser);

            emailHandler.sendMail(userRequest.getEmail(), mailRegSuccessSubject, "Hi, "+newUser.getUsername()+"\n\n"+mailRegSuccessContent);
            return responseUtil.successResponse(userRegisterResponse);
        } else {
            return responseUtil.errorResponse("Invalid OTP " + otp);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<UserLoginResponse>> loginUser(UserLoginRequest userLoginRequest) {
        log.debug(userLoginRequest);

        User user = userRepository.findByEmail(userLoginRequest.getEmail()).orElseThrow(InvalidCredentialsException::new);

        if (user == null) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        log.debug(user);

        if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword()))
            throw new InvalidCredentialsException();

        String accessToken = jwtUtils.generateAccessToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(user);

        log.debug("Access Token: " + accessToken);
        log.debug("Refresh Token: " + refreshToken);

        if (accessToken == null || accessToken.isEmpty()) {
            throw new IllegalArgumentException("Access token cannot be null or empty");
        }
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new IllegalArgumentException("Refresh token cannot be null or empty");
        }


        return responseUtil.successResponse(new UserLoginResponse(user, accessToken,refreshToken));
    }

//    @Override
//    public User loginUser(UserLoginRequest userLoginRequest) {
//        log.debug(userLoginRequest);
//
//        User user = userRepository.findByEmail(userLoginRequest.getEmail()).orElseThrow(InvalidCredentialsException::new);
//
//        if (user == null) {
//            throw new IllegalArgumentException("Invalid email or password");
//        }
//
//        log.debug(user);
//
//        if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword()))
//            throw new InvalidCredentialsException();
////
////        String accessToken = jwtUtils.generateAccessToken(user);
//        String refreshToken = jwtUtils.generateTokenFromUserDetails(user, user.getId().toString());
////
////        log.debug("Access Token: " + accessToken);
////        log.debug("Refresh Token: " + refreshToken);
////
////        if (accessToken == null || accessToken.isEmpty()) {
////            throw new IllegalArgumentException("Access token cannot be null or empty");
////        }
////        if (refreshToken == null || refreshToken.isEmpty()) {
////            throw new IllegalArgumentException("Refresh token cannot be null or empty");
////        }
//
//
////        return responseUtil.successResponse(new UserLoginResponse(user, accessToken,refreshToken));
//        return user;
//    }

    @Override
    public ResponseEntity<ResponseDTO<UserLoginResponse>> generateAccessTokenFromRefreshToken(RefreshTokenRequest refreshTokenRequest) {
//        String refreshToken = jwtUtils.getJwtFromHeader(request);

        if (jwtUtils.isTokenBlacklisted(refreshTokenRequest.getRefreshToken())) {
//            log.error("Refresh token is blacklisted");
            throw new JwtException("Refresh token is blacklisted");
        }

        String userId = jwtUtils.getUserIdFromJwtToken(refreshTokenRequest.getRefreshToken());
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new JwtException("User not found"));

        return responseUtil.successResponse(new UserLoginResponse(user,jwtUtils.generateAccessToken(user),"jnfj"));
    }

    @Override
    public String logout(HttpServletRequest request, RefreshTokenRequest refreshTokenRequest) {
        jwtUtils.blackListAccessToken(jwtUtils.getJwtFromHeader(request));
        jwtUtils.blackListRefreshToke(refreshTokenRequest.getRefreshToken());
        return "Successfully Logged out";
    }
}
