package com.robosoft.elearning.controller;

import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.modal.Test;
import com.robosoft.elearning.services.TestServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tests")
public class TestController {

    @Autowired
    private TestServices testServices;

    @GetMapping("/lessons/{lessonId}/tests")
    public ResponseEntity<ResponseDTO<List<Test>>> getTestsForLesson(@PathVariable Long lessonId) {
//        List<Test> tests = testServices.getTestsForLesson(lessonId);
        return testServices.getTestsForLesson(lessonId);
    }
}
