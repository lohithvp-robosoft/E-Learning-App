package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.request.UserRequest;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.UserRegisterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserServices {

    ResponseEntity<ResponseDTO<UserRegisterResponse>> registerUser(UserRequest userRequest, String otp);
}
