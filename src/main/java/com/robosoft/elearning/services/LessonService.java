package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.request.LessonRequest;
import com.robosoft.elearning.dto.response.*;
import com.robosoft.elearning.modal.Chapter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LessonService {
    ResponseEntity<ResponseDTO<LessonResponse>> getLessonById(long id);
    ResponseEntity<ResponseDTO<LessonResponse>> getLessonDetailsById(long lessonId);
    ResponseEntity<ResponseDTO<List<LessonResponse>>> getLessonsByChapterId(Long chapterId);
    ResponseEntity<ResponseDTO<List<CurrentlyStudyingLessonResponse1>>> getCurrentlyStudyingLessonByChapterId1(Long chapterId, HttpServletRequest request);
    ResponseEntity<ResponseDTO<LessonResponse>> createLesson(LessonRequest lessonRequest);
    ResponseEntity<ResponseDTO<LessonResponse>> updateLesson(long id, LessonRequest lessonRequest);
    ResponseEntity<ResponseDTO<Void>> deleteLesson(long id);
}