package com.robosoft.elearning.controller;


import com.robosoft.elearning.dto.request.UpdateCompletedChapterRequest;
import com.robosoft.elearning.dto.response.CompletedChapterResponse;
import com.robosoft.elearning.dto.response.UpdateCompletedChapterResponse;
import com.robosoft.elearning.services.CompletedChapterService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CompletedChapterController {

    @Autowired
    private CompletedChapterService completedChapterService;

    @GetMapping("/v1/user")
    public ResponseEntity<List<UpdateCompletedChapterResponse>> getCompletedChaptersByUser(HttpServletRequest request) {
        return completedChapterService.getCompletedChaptersByUser(request);
    }

    @PutMapping("/v1/update")
    public ResponseEntity<String> updateCompletedChapter(HttpServletRequest request, @RequestBody UpdateCompletedChapterRequest updateRequest) {
        return completedChapterService.updateCompletedChapter(request, updateRequest);
    }
}
