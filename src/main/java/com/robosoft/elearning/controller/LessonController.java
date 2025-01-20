package com.robosoft.elearning.controller;

import com.robosoft.elearning.dto.response.LessonResponse;
import com.robosoft.elearning.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class LessonController {
    @Autowired
    private LessonService lessonService;

    @GetMapping("/v1/lesson/{lessonId}")
    public ResponseEntity<LessonResponse> getLessonById(@PathVariable long lessonId) {
        return lessonService.getLessonById(lessonId);
    }

    @GetMapping("/v1/{lessonId}/details")
    public ResponseEntity<LessonResponse> getLessonDetailsById(@PathVariable long lessonId) {
        return lessonService.getLessonDetailsById(lessonId);
    }


}


