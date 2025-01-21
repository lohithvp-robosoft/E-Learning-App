package com.robosoft.elearning.controller;


import com.robosoft.elearning.dto.response.LessonContentResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.services.ContentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @GetMapping("/{lessonId}/content")
    public ResponseEntity<ResponseDTO<LessonContentResponse>> getLessonContent(@PathVariable Long lessonId, @RequestParam int pageNumber, HttpServletRequest request) {
        return contentService.getLessonContent(lessonId, pageNumber, request);
    }

}
