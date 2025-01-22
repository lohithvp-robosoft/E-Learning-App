package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.response.ContentResponseDTO;
import com.robosoft.elearning.dto.response.PaginatedContentResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ContentService {
//    ResponseEntity<ResponseDTO<LessonContentResponse>> getLessonContent(Long lessonId, int pageNumber, HttpServletRequest request);
ResponseEntity<List<ContentResponseDTO>> getPaginatedContent(Long lessonId, int pageNumber, int pageSize);
ResponseEntity<String> redirectToPage(Long lessonId, int pageNumber, int pageSize);
ResponseEntity<PaginatedContentResponseDTO> goToPage(Long lessonId, int pageNumber, int pageSize);

}
