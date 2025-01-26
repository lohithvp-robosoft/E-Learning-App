package com.robosoft.elearning.controller;

import com.robosoft.elearning.dto.response.*;
import com.robosoft.elearning.services.QuestionServices;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {

    @Autowired
    private QuestionServices questionServices;

    @GetMapping("/test/{testId}")
    public ResponseEntity<ResponseDTO<QuestionsListResponse>> getAllQuestions(@PathVariable Long testId, HttpServletRequest request){
        return questionServices.beginTheTest(testId,request);
    }

    @PostMapping("/{questionId}/test/{testId}")
    public ResponseEntity<ResponseDTO<Void>> saveOption(@PathVariable Long questionId, @PathVariable Long testId, @RequestParam Integer selectedOption, HttpServletRequest request){
        return questionServices.saveOptionForAQuestion(testId, questionId,selectedOption, request);
    }
}
