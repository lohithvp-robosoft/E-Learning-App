package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.response.ContentResponse;
import com.robosoft.elearning.dto.response.PaginatedContentResponseDTO;
import com.robosoft.elearning.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ContentService {
//    ResponseEntity<ResponseDTO<LessonContentResponse>> getLessonContent(Long lessonId, int pageNumber, HttpServletRequest request);
ResponseEntity<List<ContentResponse>> getPaginatedContent(Long lessonId, int pageNumber, int pageSize);
ResponseEntity<String> redirectToPage(Long lessonId, int pageNumber, int pageSize);
ResponseEntity<ResponseDTO<PaginatedContentResponseDTO>> goToPage(Long topicId, int pageNumber, int pageSize) ;

}
