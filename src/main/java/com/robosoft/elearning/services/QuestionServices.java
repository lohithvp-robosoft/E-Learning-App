package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.response.*;
import com.robosoft.elearning.modal.Question;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionServices {

//    ResponseEntity<ResponseDTO<QuestionResponse>> beginTest(HttpServletRequest request,Long testId);
//
//    ResponseEntity<ResponseDTO<QuestionResponse>> navigateAndSubmitAnswer(HttpServletRequest request, Long testId, Long nextQuestionId, Integer selectedOption, boolean isForward);
//
//    ResponseEntity<ResponseDTO<TestSubmitResponse>> submitTest(HttpServletRequest request, Long testId, boolean isTimeOut);
//
//    ResponseEntity<ResponseDTO<List<QuestionSetResponse>>> getQuestionSet(HttpServletRequest request, Long testId);


    ResponseEntity<ResponseDTO<QuestionsListResponse>> getAllQuestions(Long testId, HttpServletRequest request);

    ResponseEntity<ResponseDTO<Void>> saveOptionForAQuestion(Long testId, Long questionId, Integer selectedOption, HttpServletRequest request);


}
