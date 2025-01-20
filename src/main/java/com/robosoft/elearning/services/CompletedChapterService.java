package com.robosoft.elearning.services;


import com.robosoft.elearning.dto.request.UpdateCompletedChapterRequest;
import com.robosoft.elearning.dto.response.UpdateCompletedChapterResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CompletedChapterService {
    ResponseEntity<List<UpdateCompletedChapterResponse>> getCompletedChaptersByUser(HttpServletRequest request);
    ResponseEntity<String> updateCompletedChapter(HttpServletRequest request, UpdateCompletedChapterRequest updateRequest);
}
