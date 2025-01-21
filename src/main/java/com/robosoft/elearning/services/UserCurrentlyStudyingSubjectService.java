package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.response.CurrentlyStudyingResponseDTO;
import com.robosoft.elearning.dto.response.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserCurrentlyStudyingSubjectService {
    ResponseEntity<ResponseDTO<List<CurrentlyStudyingResponseDTO>>> getCurrentlyStudyingSubjects(HttpServletRequest request);
    public ResponseEntity<ResponseDTO<String>> updateLessonProgress(HttpServletRequest request, Long chapterId, Long lessonId, boolean isCompleted);
}
