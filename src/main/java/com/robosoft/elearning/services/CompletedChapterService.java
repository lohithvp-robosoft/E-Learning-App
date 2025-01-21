package com.robosoft.elearning.services;


import com.robosoft.elearning.dto.request.UpdateCompletedChapterRequest;
import com.robosoft.elearning.dto.response.CompletedChapterResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CompletedChapterService {
    ResponseEntity<ResponseDTO<List<CompletedChapterResponse>>> getCompletedChaptersByUser(HttpServletRequest request);
    public ResponseEntity<ResponseDTO<String>> updateCompletedChapter(HttpServletRequest request, UpdateCompletedChapterRequest updateRequest);
}
