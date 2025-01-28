package com.robosoft.elearning.controller;

import com.robosoft.elearning.dto.request.SubjectRequest;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.SubjectResponse;
import com.robosoft.elearning.dto.response.SubjectResponseList;
import com.robosoft.elearning.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/search/subjectName")
    public ResponseEntity<ResponseDTO<List<SubjectResponse>>> searchSubjects(@RequestParam String keyword) {
        return subjectService.searchSubjectByName(keyword);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create/subject")
    public ResponseEntity<ResponseDTO<SubjectResponse>> createSubject(@RequestBody SubjectRequest subjectRequest) {
        return subjectService.createSubject(subjectRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO<SubjectResponse>> updateSubject(@PathVariable Long id, @RequestBody SubjectRequest subjectRequest) {
        return subjectService.updateSubject(id, subjectRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteSubject(@PathVariable Long id) {
        return subjectService.deleteSubject(id);
    }

}