package com.robosoft.elearning.controller;

import com.robosoft.elearning.dto.response.ChapterResponse;
import com.robosoft.elearning.services.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @GetMapping("/v1/chapter/{chapterId}")
    public ResponseEntity<ChapterResponse> getChapterById(@PathVariable long chapterId) {
        return chapterService.getChapterById(chapterId);
    }


    @GetMapping("v1/{chapterId}/lessons")
    public ResponseEntity<ChapterResponse> getLessonsByChapterId(@PathVariable long chapterId) {
        return chapterService.getChapterWithLessons(chapterId);
    }

}
