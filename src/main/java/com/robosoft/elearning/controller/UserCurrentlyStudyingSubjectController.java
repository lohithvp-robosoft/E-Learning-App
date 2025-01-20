package com.robosoft.elearning.controller;


import com.robosoft.elearning.dto.response.CurrentlyStudyingResponseDTO;
import com.robosoft.elearning.services.UserCurrentlyStudyingSubjectService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserCurrentlyStudyingSubjectController {
    @Autowired
    private UserCurrentlyStudyingSubjectService service;

    @GetMapping("v1/currently-studying-subject")
    public ResponseEntity<List<CurrentlyStudyingResponseDTO>> getCurrentlyStudyingSubjects(HttpServletRequest request) {
        return service.getCurrentlyStudyingSubjects(request);
    }
}
