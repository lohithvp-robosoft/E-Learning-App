package com.robosoft.elearning.controller;

import com.robosoft.elearning.dto.request.ChapterRequest;
import com.robosoft.elearning.dto.response.ChapterResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.services.ChapterService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/chapters/subject/{subjectId}")
    public ResponseEntity<ResponseDTO<Map<String, Object>>> getChaptersBySubjectId(
            @PathVariable Long subjectId, HttpServletRequest request) {
        return chapterService.getChaptersBySubjectId(subjectId, request);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<ChapterResponse>> updateChapter(@PathVariable Long id, @RequestBody ChapterRequest chapterRequest) {
        return chapterService.updateChapter(id, chapterRequest);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createChapter")
    public ResponseEntity<ResponseDTO<ChapterResponse>> createChapter(@RequestBody ChapterRequest chapterRequest) {
        return chapterService.createChapter(chapterRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteChapter(@PathVariable Long id) {
        return chapterService.deleteChapter(id);
    }

}