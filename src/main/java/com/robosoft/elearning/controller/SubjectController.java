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
@RequestMapping("/api/v1")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @GetMapping("/subjects")
    public ResponseEntity<ResponseDTO<SubjectResponseList>> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/subjects/{id}")
    public ResponseEntity<ResponseDTO<SubjectResponse>> getSubjectById(@PathVariable Long id) {
        return subjectService.getSubjectById(id);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<ResponseDTO<SubjectResponse>> searchSubjectByName(@PathVariable String name) {
        return subjectService.searchSubjectByName(name);
    }

}

