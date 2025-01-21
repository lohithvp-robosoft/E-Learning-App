package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.SubjectResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SubjectService {
    ResponseEntity<ResponseDTO<List<SubjectResponse>>> getAllSubjects();
    ResponseEntity<ResponseDTO<SubjectResponse>> getSubjectById(Long id);
    ResponseEntity<ResponseDTO<SubjectResponse>> searchSubjectByName(String name);
    ResponseEntity<ResponseDTO<Void>> assignSubjectToUser(HttpServletRequest request, Long subjectId);
    ResponseEntity<ResponseDTO<Void>> assignSubjectsToUser(HttpServletRequest request, List<Long> subjectIds);




}
