package com.robosoft.elearning.controller;

import com.robosoft.elearning.dto.response.ChapterNameResponse;
import com.robosoft.elearning.dto.response.LessonResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
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

    @GetMapping("/v1/chapters/{chapterId}/lessons")
    public ResponseEntity<ResponseDTO<List<LessonResponse>>> getLessonsByChapterId(@PathVariable Long chapterId) {
        return lessonService.getLessonsByChapterId(chapterId);
    }

    @GetMapping("/{chapterId}/lesson/{lessonId}")
    public ResponseEntity<ResponseDTO<ChapterNameResponse>> getChapterWithLesson(
            @PathVariable long chapterId,
            @PathVariable int lessonId) {
        return lessonService.getChapterWithLesson(chapterId, lessonId);
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO<List<ChapterNameResponse>>> getAllChaptersWithLessons() {
        return lessonService.getAllChaptersWithLessons();
    }
}


