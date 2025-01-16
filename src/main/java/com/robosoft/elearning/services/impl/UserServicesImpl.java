package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.request.BaseRegisterRequest;
import com.robosoft.elearning.dto.request.LoginRequest;
import com.robosoft.elearning.dto.request.RefreshTokenRequest;
import com.robosoft.elearning.dto.request.UpdateUserRequest;
import com.robosoft.elearning.dto.response.RefreshTokenResponse;
import com.robosoft.elearning.dto.response.RegisterResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.LoginResponse;
import com.robosoft.elearning.exception.EmailAlreadyExistsException;
import com.robosoft.elearning.exception.InvalidCredentialsException;
import com.robosoft.elearning.exception.JwtException;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.modal.User;
import com.robosoft.elearning.repository.UserRepository;
import com.robosoft.elearning.services.OtpServices;
import com.robosoft.elearning.services.UserServices;
import com.robosoft.elearning.util.EmailHandler;
import com.robosoft.elearning.util.FileStorageUtil;
import com.robosoft.elearning.util.ObjectMapperUtil;
import com.robosoft.elearning.util.ResponseUtil;
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

@Service
public class UserServicesImpl implements UserServices {

    private static final Log log = LogFactory.getLog(UserServicesImpl.class);
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

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private FileStorageUtil fileStorageUtil;

    @Value("${email.already.exist.message}")
    private String emailAlreadyExistMessage;

    @Value("${token.blacklistedMessage}")
    private String tokenBlacklistMessage;

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

            emailHandler.sendMail(registerRequest.getEmail(), mailRegSuccessSubject, "Hi, "+newUser.getUsername()+"\n\n"+mailRegSuccessContent);

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

        return responseUtil.successResponse(new LoginResponse(user, accessToken,refreshToken));
    }

    @Override
    public ResponseEntity<ResponseDTO<RefreshTokenResponse>> generateAccessTokenFromRefreshToken(RefreshTokenRequest refreshTokenRequest) {

        if (jwtUtils.isTokenBlacklisted(refreshTokenRequest.getRefreshToken())) {
            throw new JwtException(tokenBlacklistMessage);
        }
        if(jwtUtils.isAccessToken(refreshTokenRequest.getRefreshToken())) throw new JwtException("Please use Valid refresh Token");

        String userId = jwtUtils.getUserIdFromJwtToken(refreshTokenRequest.getRefreshToken());
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new JwtException("User not found"));

        return responseUtil.successResponse(new RefreshTokenResponse(user,jwtUtils.generateAccessToken(user)));
    }

    @Override
    public ResponseEntity<ResponseDTO<Void>> logout(HttpServletRequest request, RefreshTokenRequest refreshTokenRequest) {
        jwtUtils.blackListAccessToken(jwtUtils.getJwtFromHeader(request));
        jwtUtils.blackListRefreshToken(refreshTokenRequest.getRefreshToken());
        return responseUtil.successResponse(null,logoutMessage);
    }

    public ResponseEntity<ResponseDTO<User>> update(UpdateUserRequest updateUserRequest, MultipartFile file) throws IOException {
        User user = objectMapperUtil.convert(updateUserRequest,User.class);
        userRepository.save(user);

        if (file != null && !file.isEmpty()) {
            String folder = "user-profile-images";
            String imageUrl = fileStorageUtil.storeFile(file, folder, user.getId());
            user.setImageUrl(imageUrl);
        }
        userRepository.save(user);
        return responseUtil.successResponse(user,"User Updated Successfully");
    }
}
