package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.request.UserRequest;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.UserRegisterResponse;
import com.robosoft.elearning.exception.EmailAlreadyExistsException;
import com.robosoft.elearning.modal.User;
import com.robosoft.elearning.repository.UserRepository;
import com.robosoft.elearning.services.OtpServices;
import com.robosoft.elearning.services.UserServices;
import com.robosoft.elearning.util.EmailHandler;
import com.robosoft.elearning.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServicesImpl implements UserServices {

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
}
