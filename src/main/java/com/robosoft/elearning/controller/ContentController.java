package com.robosoft.elearning.controller;


import com.robosoft.elearning.dto.response.ContentResponse;
import com.robosoft.elearning.dto.response.PaginatedContentResponseDTO;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.services.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @GetMapping("/paginated")
    public ResponseEntity<List<ContentResponse>> getPaginatedContent(@RequestParam Long lessonId,
                                                                     @RequestParam int pageNumber,
                                                                     @RequestParam int pageSize) {
        return contentService.getPaginatedContent(lessonId, pageNumber, pageSize);
    }

    @GetMapping("/redirect")
    public ResponseEntity<String> redirectToPage(@RequestParam Long lessonId,
                                                 @RequestParam int pageNumber,
                                                 @RequestParam int pageSize) {
        return contentService.redirectToPage(lessonId, pageNumber, pageSize);
    }

    @GetMapping("/go-to-page")
    public ResponseEntity<ResponseDTO<PaginatedContentResponseDTO>> goToPage(
            @RequestParam Long topicId,
            @RequestParam int pageNumber,
            @RequestParam int pageSize
    ) {
        return contentService.goToPage(topicId, pageNumber, pageSize);
    }


//    @GetMapping("/{lessonId}/content")
//    public ResponseEntity<ResponseDTO<LessonContentResponse>> getLessonContent(@PathVariable Long lessonId, @RequestParam int pageNumber, HttpServletRequest request) {
//        return contentService.getLessonContent(lessonId, pageNumber, request);
//    }

}
