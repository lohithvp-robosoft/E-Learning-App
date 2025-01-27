package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.request.CreateQuestionRequest;
import com.robosoft.elearning.dto.request.UpdateQuestionRequest;
import com.robosoft.elearning.dto.response.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface QuestionServices {

//    ResponseEntity<ResponseDTO<QuestionResponse>> beginTest(HttpServletRequest request,Long testId);
//
//    ResponseEntity<ResponseDTO<QuestionResponse>> navigateAndSubmitAnswer(HttpServletRequest request, Long testId, Long nextQuestionId, Integer selectedOption, boolean isForward);
//
//    ResponseEntity<ResponseDTO<TestSubmitResponse>> submitTest(HttpServletRequest request, Long testId, boolean isTimeOut);
//
//    ResponseEntity<ResponseDTO<List<QuestionSetResponse>>> getQuestionSet(HttpServletRequest request, Long testId);
ResponseEntity<ResponseDTO<QuestionResponse>> createQuestion(CreateQuestionRequest request);
    ResponseEntity<ResponseDTO<QuestionResponse>> updateQuestion(Long questionId, UpdateQuestionRequest request);
    ResponseEntity<ResponseDTO<Void>> deleteQuestion(Long questionId);
    ResponseEntity<ResponseDTO<QuestionsListResponse>> beginTheTest(Long testId, HttpServletRequest request);

    ResponseEntity<ResponseDTO<Void>> saveOptionForAQuestion(Long testId, Long questionId, Integer selectedOption, HttpServletRequest request);


}
