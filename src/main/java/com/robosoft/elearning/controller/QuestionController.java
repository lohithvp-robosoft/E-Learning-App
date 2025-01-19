package com.robosoft.elearning.controller;

import com.robosoft.elearning.dto.response.QuestionResponse;
import com.robosoft.elearning.dto.response.QuestionSetResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.TestSubmitResponse;
import com.robosoft.elearning.services.QuestionServices;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {

    @Autowired
    private QuestionServices questionServices;

    @GetMapping("/tests/{testId}/begin")
    public ResponseEntity<ResponseDTO<QuestionResponse>> beginTest(
            HttpServletRequest request,
            @PathVariable Long testId) {
        return questionServices.beginTest(request,testId);
    }

    @GetMapping("/tests/{testId}/navigate")
    public ResponseEntity<ResponseDTO<QuestionResponse>> navigateAndSubmitAnswer(
            HttpServletRequest request,
            @PathVariable Long testId,
            @RequestParam(required = false) Long nextQuestionId,
            @RequestParam(required = false) Integer selectedOption,
            @RequestParam(required = false, defaultValue = "true") boolean isForward) {
        return questionServices.navigateAndSubmitAnswer(request, testId, nextQuestionId, selectedOption, isForward);
    }

    @GetMapping("/tests/{testId}/submit")
    public ResponseEntity<ResponseDTO<TestSubmitResponse>> submitTest(
            HttpServletRequest request,
            @PathVariable Long testId,
            @RequestParam(defaultValue = "false") boolean isTimeOut) {
        return questionServices.submitTest(request, testId, isTimeOut);
    }

    @GetMapping("/tests/{testId}/set")
    public ResponseEntity<ResponseDTO<List<QuestionSetResponse>>> getQuestionSet(
            HttpServletRequest request,
            @PathVariable Long testId) {
        return questionServices.getQuestionSet(request, testId);
    }
}
