package com.robosoft.elearning.controller;


import com.robosoft.elearning.dto.response.CurrentlyStudyingResponseDTO;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.modal.Lesson;
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

    @GetMapping("/currently-studying-subjects/subject/{subjectId}")
    public ResponseEntity<ResponseDTO<List<CurrentlyStudyingResponseDTO>>> getCurrentlyStudyingSubjects(HttpServletRequest request) {
        return userCurrentlyStudyingSubjectService.getCurrentlyStudyingSubjects(request);
    }

    @PutMapping("/update-progress/chapter/{chapterId}/lesson/{lessonId}")
    public ResponseEntity<ResponseDTO<String>> updateLessonProgress(HttpServletRequest request, @PathVariable Long chapterId, @PathVariable Lesson lessonId, @RequestParam boolean isCompleted) {
        return userCurrentlyStudyingSubjectService.updateLessonProgress(request, chapterId, lessonId, isCompleted);
    }

    @PostMapping("/assign-chapter/{chapterId}/subject/{subjectId}")
    public void assignChapterToUser(HttpServletRequest request, @PathVariable long chapterId) {
        userCurrentlyStudyingSubjectService.assignChapterToUser(request, chapterId);
    }





}
