package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.request.LikeLessonRequestDTO;
import com.robosoft.elearning.dto.response.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface UserLikedService {
    ResponseEntity<ResponseDTO<String>> likeLesson(LikeLessonRequestDTO requestDTO, HttpServletRequest request);
}
