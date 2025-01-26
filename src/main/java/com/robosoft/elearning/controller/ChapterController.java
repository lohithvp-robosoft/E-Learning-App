package com.robosoft.elearning.controller;

import com.robosoft.elearning.dto.request.ChapterRequest;
import com.robosoft.elearning.dto.response.ChapterResponse;
import com.robosoft.elearning.dto.response.ChapterSummaryResponse;
import com.robosoft.elearning.dto.response.CurrentlyStudyingLessonResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
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

    @GetMapping("/{subjectId}/chapters")
    public ResponseEntity<ResponseDTO<Map<String, Object>>> getChaptersBySubjectId(
            @PathVariable Long subjectId, HttpServletRequest request) {
        return chapterService.getChaptersBySubjectId(subjectId, request);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<ChapterResponse>> updateChapter(@PathVariable Long id, @RequestBody ChapterRequest chapterRequest) {
        return chapterService.updateChapter(id, chapterRequest);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<ChapterResponse>> createChapter(@RequestBody ChapterRequest chapterRequest) {
        return chapterService.createChapter(chapterRequest);
    }

    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteChapter(@PathVariable Long id) {
        return chapterService.deleteChapter(id);
    }

}