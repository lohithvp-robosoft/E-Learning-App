package com.robosoft.elearning.controller;

import com.robosoft.elearning.dto.request.CreateQuestionRequest;
import com.robosoft.elearning.dto.request.UpdateQuestionRequest;
import com.robosoft.elearning.dto.response.*;
import com.robosoft.elearning.services.QuestionServices;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

//    @PostMapping("/{questionId}/test/{testId}")
//    public ResponseEntity<ResponseDTO<Void>> savedOption(){
//        return null;
//    }

//    @GetMapping("/tests/{testId}/begin")
//    public ResponseEntity<ResponseDTO<QuestionResponse>> beginTest(
//            HttpServletRequest request,
//            @PathVariable Long testId) {
//        return questionServices.beginTest(request,testId);
//    }

//    @GetMapping("/tests/{testId}/navigate")
//    public ResponseEntity<ResponseDTO<QuestionResponse>> navigateAndSubmitAnswer(
//            HttpServletRequest request,
//            @PathVariable Long testId,
//            @RequestParam(required = false) Long nextQuestionId,
//            @RequestParam(required = false) Integer selectedOption,
//            @RequestParam(required = false, defaultValue = "true") boolean isForward) {
//        return questionServices.navigateAndSubmitAnswer(request, testId, nextQuestionId, selectedOption, isForward);
//    }

//    @GetMapping("/tests/{testId}/submit")
//    public ResponseEntity<ResponseDTO<TestSubmitResponse>> submitTest(
//            HttpServletRequest request,
//            @PathVariable Long testId,
//            @RequestParam(defaultValue = "false") boolean isTimeOut) {
//        return questionServices.submitTest(request, testId, isTimeOut);
//    }

//    @GetMapping("/tests/{testId}/set")
//    public ResponseEntity<ResponseDTO<List<QuestionSetResponse>>> getQuestionSet(
//            HttpServletRequest request,
//            @PathVariable Long testId) {
//        return questionServices.getQuestionSet(request, testId);
//    }







    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-question")
    public ResponseEntity<ResponseDTO<QuestionResponse>> createQuestion(@RequestBody CreateQuestionRequest request) {
        return questionServices.createQuestion(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-question/{questionId}")
    public ResponseEntity<ResponseDTO<QuestionResponse>> updateQuestion(
            @PathVariable Long questionId, @RequestBody UpdateQuestionRequest request) {
        return questionServices.updateQuestion(questionId, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete-question/{questionId}")
    public ResponseEntity<ResponseDTO<Void>> deleteQuestion(@PathVariable Long questionId) {
        return questionServices.deleteQuestion(questionId);
    }
}
