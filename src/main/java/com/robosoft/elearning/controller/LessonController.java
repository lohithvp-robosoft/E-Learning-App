package com.robosoft.elearning.controller;

import com.robosoft.elearning.dto.response.*;
import com.robosoft.elearning.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class LessonController {
    @Autowired
    private LessonService lessonService;

    @GetMapping("/lesson/{id}")
    public ResponseEntity<ResponseDTO<LessonResponse>> getLessonById(@PathVariable long id) {
        return lessonService.getLessonById(id);
    }

    @GetMapping("/details/{lessonId}")
    public ResponseEntity<ResponseDTO<LessonResponse>> getLessonDetailsById(@PathVariable long lessonId) {
        return lessonService.getLessonDetailsById(lessonId);
    }

    @GetMapping("/chapters/{chapterId}/lessons")
    public ResponseEntity<ResponseDTO<List<LessonResponse>>> getLessonsByChapterId(@PathVariable Long chapterId) {
        return lessonService.getLessonsByChapterId(chapterId);
    }

    @GetMapping("/chapter/{chapterId}/lessons")
    public ResponseEntity<ResponseDTO<ChapterLessonsResponse>> getLessonsDetailsByChapterId(
            @PathVariable long chapterId) {
        return lessonService.getLessonsDetailsByChapterId(chapterId);
    }

    @GetMapping("/chapter/{chapterId}/lessons/topics")
    public ResponseEntity<ResponseDTO<ChapterLessonTopicResponse>> getLessonsWithTopicsByChapterId(@PathVariable long chapterId) {
        return lessonService.getLessonsWithTopicsByChapterId(chapterId);
    }

}


