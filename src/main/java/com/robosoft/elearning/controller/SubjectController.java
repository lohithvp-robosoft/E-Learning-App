package com.robosoft.elearning.controller;

import com.robosoft.elearning.dto.request.AssignSubjectRequestDTO;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.SubjectResponse;
import com.robosoft.elearning.dto.response.SubjectResponseList;
import com.robosoft.elearning.services.SubjectService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @GetMapping("/v1/subjects")
    public ResponseEntity<ResponseDTO<SubjectResponseList>> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/v1/subjects/{id}")
    public ResponseEntity<ResponseDTO<SubjectResponse>> getSubjectById(@PathVariable Long id) {
        return subjectService.getSubjectById(id);
    }

    @GetMapping("/v1/search/{subjectName}")
    public ResponseEntity<ResponseDTO<SubjectResponse>> searchSubjectByName(@PathVariable String name) {
        return subjectService.searchSubjectByName(name);
    }

    @PostMapping("/v1/assign-subject")
    public ResponseEntity<ResponseDTO<Void>> assignSubjectToUser(@RequestBody AssignSubjectRequestDTO requestDTO, HttpServletRequest request) {
        return subjectService.assignSubjectsToUser(request, requestDTO.getSubjectIds());
    }
}

