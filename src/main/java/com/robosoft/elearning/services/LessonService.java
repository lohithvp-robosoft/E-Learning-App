package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.response.LessonResponse;
import org.springframework.http.ResponseEntity;

public interface LessonService {
    ResponseEntity<LessonResponse> getLessonById(long id);
    ResponseEntity<LessonResponse> getLessonDetailsById(long lessonId);
}
