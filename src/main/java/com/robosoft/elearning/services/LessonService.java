package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.response.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LessonService {
    ResponseEntity<ResponseDTO<LessonResponse>> getLessonById(long id);
    ResponseEntity<ResponseDTO<LessonResponse>> getLessonDetailsById(long lessonId);
    // ResponseEntity<ResponseDTO<ChapterNameResponse>> getChapterWithLesson(long chapterId, int lessonId);
//    ResponseEntity<ResponseDTO<ChapterLessonsResponse>> getLessonsDetailsByChapterId(long chapterId);
    ResponseEntity<ResponseDTO<List<LessonResponse>>> getLessonsByChapterId(Long chapterId);
    ResponseEntity<ResponseDTO<ChapterLessonTopicResponse>> getLessonsWithTopicsByChapterId(long chapterId);
    ResponseEntity<ResponseDTO<List<LessonWithTopicResponse>>> getLessonsDetails(HttpServletRequest request);
    // ResponseEntity<ResponseDTO<List<ChapterNameResponse>>> getAllChaptersWithLessons();
}