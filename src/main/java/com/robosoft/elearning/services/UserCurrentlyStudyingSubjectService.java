package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.response.CurrentlyStudyingResponseDTO;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.modal.Lesson;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserCurrentlyStudyingSubjectService {
    ResponseEntity<ResponseDTO<List<CurrentlyStudyingResponseDTO>>> getCurrentlyStudyingSubjects(HttpServletRequest request);
    ResponseEntity<ResponseDTO<String>> updateLessonProgress(HttpServletRequest request, Long chapterId, Lesson lessonId, boolean isCompleted);
    void assignChapterToUser(HttpServletRequest request, long chapterId);
}
