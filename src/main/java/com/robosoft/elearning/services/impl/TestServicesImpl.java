package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.controller.TestController;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.TestResponse;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.modal.Test;
import com.robosoft.elearning.repository.TestRepository;
import com.robosoft.elearning.services.TestServices;
import com.robosoft.elearning.util.EntityMapperUtil;
import com.robosoft.elearning.util.ObjectMapperUtil;
import com.robosoft.elearning.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class TestServicesImpl implements TestServices {


    @Autowired
    private TestRepository testRepository;

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private EntityMapperUtil entityMapperUtil;

    @Override
    public ResponseEntity<ResponseDTO<TestResponse>> getOneTest(Long testId) {
        Test test = testRepository.findById(testId).orElseThrow(() -> new NotFoundException("Test Not Found"));
        TestResponse testResponse = entityMapperUtil.convertToTestResponse(test);

        return responseUtil.successResponse(testResponse);
    }

    @Override
    public ResponseEntity<ResponseDTO<List<TestResponse>>> getTestsForLesson(Long lessonId) {
        List<Test> tests = testRepository.findByLessonId(lessonId);
        if(tests.isEmpty()){
            throw  new NotFoundException("No Test Available");
        }
        List<TestResponse> testResponses = tests.stream()
                .map(test -> entityMapperUtil.convertToTestResponse(test))
                .toList();

        return responseUtil.successResponse(testResponses);
    }
}
