package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.response.QuestionResponse;
import com.robosoft.elearning.dto.response.QuestionSetResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.TestSubmitResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionServices {

    ResponseEntity<ResponseDTO<QuestionResponse>> beginTest(HttpServletRequest request,Long testId);

    ResponseEntity<ResponseDTO<QuestionResponse>> navigateAndSubmitAnswer(HttpServletRequest request, Long testId, Long nextQuestionId, Integer selectedOption, boolean isForward);

    ResponseEntity<ResponseDTO<TestSubmitResponse>> submitTest(HttpServletRequest request, Long testId, boolean isTimeOut);

    ResponseEntity<ResponseDTO<List<QuestionSetResponse>>> getQuestionSet(HttpServletRequest request, Long testId);
}
