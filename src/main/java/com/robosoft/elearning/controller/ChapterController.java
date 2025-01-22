package com.robosoft.elearning.controller;

import com.robosoft.elearning.dto.response.ChapterResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.services.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;


    @GetMapping("/v1/chapters")
    public ResponseEntity<ResponseDTO<List<ChapterResponse>>> getAllChapters() {
        return chapterService.getAllChapters();
    }

    @GetMapping("/v1/chapter/{id}")
    public ResponseEntity<ResponseDTO<ChapterResponse>> getChapterById(@PathVariable long id) {
        return chapterService.getChapterById(id);
    }

//    @GetMapping("/v1/chapter/{chapterId}/lessons")
//    public ResponseEntity<ResponseDTO<ChapterResponse>> getChapterWithLessons(@PathVariable long chapterId) {
//        return chapterService.getChapterWithLessons(chapterId);
//    }







    @GetMapping("/v1/chapters/subject/{subjectId}")
    public ResponseEntity<ResponseDTO<List<ChapterResponse>>> getChaptersBySubjectId(@PathVariable Long subjectId) {
        return chapterService.getChaptersBySubjectId(subjectId);
    }
//
//    @GetMapping("/v1/chapters/with-lessons")
//    public ResponseEntity<ResponseDTO<List<ChapterResponse>>> getAllChaptersWithLessons() {
//        return chapterService.getAllChaptersWithLessons();
//    }
}
