package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.TestResponse;
import com.robosoft.elearning.dto.response.TestSubmitResponse;
import com.robosoft.elearning.modal.Test;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public interface TestServices {

    ResponseEntity<ResponseDTO<TestResponse>> getOneTest(Long testId);
    ResponseEntity<ResponseDTO<List<TestResponse>>> getTestsForLesson(Long lessonId);

    ResponseEntity<ResponseDTO<TestSubmitResponse>> submitTest(Long testId, HttpServletRequest request, boolean isTimeOut);
}
