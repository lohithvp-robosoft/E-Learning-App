package com.robosoft.elearning.controller;


import com.robosoft.elearning.dto.request.ContentRequest;
import com.robosoft.elearning.dto.response.ContentResponse;
import com.robosoft.elearning.dto.response.PaginatedContentResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.services.ContentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<String>> createContent(@RequestBody ContentRequest contentRequest) {
        return contentService.createContent(contentRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{contentId}")
    public ResponseEntity<ResponseDTO<String>> updateContent(
            @PathVariable Long contentId,
            @RequestBody ContentRequest contentRequest) {

        return contentService.updateContent(contentId, contentRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/content/{contentId}")
    public ResponseEntity<ResponseDTO<String>> deleteContent(@PathVariable Long contentId) {
        return contentService.deleteContent(contentId);
    }

}