package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.response.LessonContentResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface ContentService {
    ResponseEntity<ResponseDTO<LessonContentResponse>> getLessonContent(Long lessonId, int pageNumber, HttpServletRequest request);
}
