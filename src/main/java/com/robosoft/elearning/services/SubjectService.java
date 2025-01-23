package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.SubjectResponse;
import com.robosoft.elearning.dto.response.SubjectResponseList;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SubjectService {
    ResponseEntity<ResponseDTO<SubjectResponseList>> getAllSubjects();
    ResponseEntity<ResponseDTO<SubjectResponse>> getSubjectById(Long id);
    ResponseEntity<ResponseDTO<SubjectResponse>> searchSubjectByName(String name);




}