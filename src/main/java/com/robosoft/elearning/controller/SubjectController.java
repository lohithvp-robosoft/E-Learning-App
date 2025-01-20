package com.robosoft.elearning.controller;

import com.robosoft.elearning.dto.response.ChapterResponse;
import com.robosoft.elearning.dto.response.LessonResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.SubjectResponse;
import com.robosoft.elearning.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @GetMapping("/v1/subjects")
    public ResponseEntity<ResponseDTO<List<SubjectResponse>>> getAllSubjects() {
        return subjectService.getAllSubjects();

    }

    @GetMapping("/v1/subjects/{id}")
    public ResponseEntity<ResponseDTO<SubjectResponse>> getSubjectById(@PathVariable Long id) {
        return subjectService.getSubjectById(id);
    }

    @GetMapping("/v1/search/{name}")
    public ResponseEntity<ResponseDTO<SubjectResponse>> searchSubjectByName(@PathVariable String name) {
        return subjectService.searchSubjectByName(name);
    }

    @GetMapping("/v1/{subjectId}/chapters")
    public ResponseEntity<ResponseDTO<List<ChapterResponse>>> getChaptersBySubjectId(@PathVariable Long subjectId) {
        return subjectService.getChaptersBySubjectId(subjectId);
    }

    @GetMapping("/v1/chapters/{chapterId}/lessons")
    public ResponseEntity<ResponseDTO<List<LessonResponse>>> getLessonsByChapterId(@PathVariable Long chapterId) {
        return subjectService.getLessonsByChapterId(chapterId);
    }

}

