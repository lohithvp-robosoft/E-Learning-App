package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.request.ContentRequest;
import com.robosoft.elearning.dto.response.PaginatedContentResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface ContentService {

    ResponseEntity<ResponseDTO<String>> createContent(ContentRequest contentRequest);

    ResponseEntity<ResponseDTO<String>> updateContent(Long contentId, ContentRequest contentRequest);

    ResponseEntity<ResponseDTO<String>> deleteContent(Long contentId);
    ResponseEntity<ResponseDTO<PaginatedContentResponse>> goToPage(Long lessonId, Long topicId, int pageNumber, HttpServletRequest request);
}