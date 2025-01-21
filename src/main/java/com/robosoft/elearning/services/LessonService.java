package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.response.LessonResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LessonService {
    ResponseEntity<ResponseDTO<LessonResponse>> getLessonById(long id);
    ResponseEntity<ResponseDTO<LessonResponse>> getLessonDetailsById(long lessonId);

    ResponseEntity<ResponseDTO<List<LessonResponse>>> getLessonsByChapterId(Long chapterId);
}
