package com.robosoft.elearning.controller;

import com.robosoft.elearning.dto.request.LessonRequest;
import com.robosoft.elearning.dto.response.*;
import com.robosoft.elearning.services.LessonService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/lesson-by-chapter/subject/{subjectId}")
    ResponseEntity<ResponseDTO<List<CurrentlyStudyingLessonResponse1>>> getCurrentlyStudyingLessonByChapterId1(@PathVariable Long subjectId, HttpServletRequest request){
        return lessonService.getCurrentlyStudyingLessonByChapterId1(subjectId, request);

    }


    @PostMapping("/createLesson")
    public ResponseEntity<ResponseDTO<LessonResponse>> createLesson(@RequestBody LessonRequest lessonRequest) {
        return lessonService.createLesson(lessonRequest);
    }

    @PutMapping("/updateLesson/{id}")
    public ResponseEntity<ResponseDTO<LessonResponse>> updateLesson(
            @PathVariable long id,
            @RequestBody LessonRequest lessonRequest) {
        return lessonService.updateLesson(id, lessonRequest);
    }

    @DeleteMapping("/deleteLesson/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteLesson(@PathVariable long id) {
        return lessonService.deleteLesson(id);
    }
}