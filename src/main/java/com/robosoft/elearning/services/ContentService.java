package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.response.LessonContentResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface ContentService {
    ResponseEntity<LessonContentResponse> getLessonContent(Long lessonId, int pageNumber, HttpServletRequest request);
}
