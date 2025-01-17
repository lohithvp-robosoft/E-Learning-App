package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.controller.TestController;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.modal.Test;
import com.robosoft.elearning.repository.TestRepository;
import com.robosoft.elearning.services.TestServices;
import com.robosoft.elearning.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServicesImpl implements TestServices {


    @Autowired
    private TestRepository testRepository;

    @Autowired
    private ResponseUtil responseUtil;

    @Override
    public ResponseEntity<ResponseDTO<List<Test>>> getTestsForLesson(Long lessonId) {
        List<Test> tests = testRepository.findByLessonId(lessonId);
        if(tests.isEmpty()){
            throw  new NotFoundException("No Test Available");
        }
        return responseUtil.successResponse(testRepository.findByLessonId(lessonId));
    }
}
