package com.robosoft.elearning.controller;

import com.robosoft.elearning.dto.response.ChapterResponse;
import com.robosoft.elearning.dto.response.CurrentlyStudyingSubjectResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.SubjectResponseList;
import com.robosoft.elearning.services.ChapterService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;


    @GetMapping("/chapters")
    public ResponseEntity<ResponseDTO<List<ChapterResponse>>> getAllChapters() {
        return chapterService.getAllChapters();
    }

    @GetMapping("/chapter/{id}")
    public ResponseEntity<ResponseDTO<ChapterResponse>> getChapterById(@PathVariable long id) {
        return chapterService.getChapterById(id);
    }

//    @GetMapping("/chapters/subject/{subjectId}")
//    public ResponseEntity<?> getChaptersBySubjectId(@PathVariable Long subjectId) {
//        return chapterService.getChaptersBySubjectId(subjectId);
//    }

@GetMapping("/chapters/subject/{subjectId}")
    public ResponseEntity<ResponseDTO<CurrentlyStudyingSubjectResponse>> getChaptersDetailsBySubjectId(
            @PathVariable Long subjectId,
            HttpServletRequest request) {
        return chapterService.getChaptersDetailsBySubjectId(subjectId, request);
    }
}