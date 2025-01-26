package com.robosoft.elearning.controller;


import com.robosoft.elearning.dto.request.ContentRequest;
import com.robosoft.elearning.dto.response.ContentResponse;
import com.robosoft.elearning.dto.response.PaginatedContentResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.services.ContentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @GetMapping("/go-to-page/topics/lesson")
    public ResponseEntity<ResponseDTO<PaginatedContentResponse>> goToPage(
            @RequestParam Long lessonId,
            @RequestParam Long topicId,
            @RequestParam int pageNumber,
            HttpServletRequest request) {
        return contentService.goToPage(lessonId, topicId, pageNumber, request);
    }


    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<String>> createContent(@RequestBody ContentRequest contentRequest) {
        return contentService.createContent(contentRequest);
    }


    @PutMapping("/update/{contentId}")
    public ResponseEntity<ResponseDTO<String>> updateContent(
            @PathVariable Long contentId,
            @RequestBody ContentRequest contentRequest) {

        return contentService.updateContent(contentId, contentRequest);
    }

    @DeleteMapping("/delete/{contentId}")
    public ResponseEntity<ResponseDTO<String>> deleteContent(@PathVariable Long contentId) {
        return contentService.deleteContent(contentId);
    }

}