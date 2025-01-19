package com.robosoft.elearning.controller;

import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.TestResponse;
import com.robosoft.elearning.services.TestServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tests")
public class TestController {

    @Autowired
    private TestServices testServices;

    @GetMapping("/lessons/{lessonId}")
    public ResponseEntity<ResponseDTO<List<TestResponse>>> getTestsForLesson(@PathVariable Long lessonId) {
        return testServices.getTestsForLesson(lessonId);
    }

    @GetMapping("/{testId}")
    public ResponseEntity<ResponseDTO<TestResponse>> getTest(@PathVariable Long testId) {
        return testServices.getOneTest(testId);
    }


}
