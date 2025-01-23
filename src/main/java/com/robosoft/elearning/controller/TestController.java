package com.robosoft.elearning.controller;

import com.google.firebase.database.core.Repo;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.TestResponse;
import com.robosoft.elearning.dto.response.TestSubmitResponse;
import com.robosoft.elearning.services.TestServices;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{testId}/submit")
    public ResponseEntity<ResponseDTO<TestSubmitResponse>> submitTest(@PathVariable Long testId, boolean isTimeout, HttpServletRequest request){
        return testServices.submitTest(testId, request,isTimeout);
    }
}
