package com.robosoft.elearning.controller;


import com.robosoft.elearning.dto.response.CurrentlyStudyingResponseDTO;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.services.UserCurrentlyStudyingSubjectService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserCurrentlyStudyingSubjectController {
    @Autowired
    private UserCurrentlyStudyingSubjectService userCurrentlyStudyingSubjectService;

    @GetMapping("/currently-studying-subjects")
    public ResponseEntity<ResponseDTO<List<CurrentlyStudyingResponseDTO>>> getCurrentlyStudyingSubjects(HttpServletRequest request) {
        return userCurrentlyStudyingSubjectService.getCurrentlyStudyingSubjects(request);
    }

    @PutMapping("/update-progress/chapter/{chapterId}/lesson/{lessonId}")
    public ResponseEntity<ResponseDTO<String>> updateLessonProgress(HttpServletRequest request, @PathVariable Long chapterId, @PathVariable Long lessonId, @RequestParam boolean isCompleted) {
        return userCurrentlyStudyingSubjectService.updateLessonProgress(request, chapterId, lessonId, isCompleted);
    }

}
