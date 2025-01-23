package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.UserTestScoreResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResultServices {
    ResponseEntity<ResponseDTO<List<UserTestScoreResponse>>> getAllScoreOfAUser(HttpServletRequest request);

    ResponseEntity<ResponseDTO<List<UserTestScoreResponse>>> getAllScoreOfAUserBySubjectId(Long subjectId, HttpServletRequest request);

}
