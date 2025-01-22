package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.response.ContentResponseDTO;
import com.robosoft.elearning.dto.response.PaginatedContentResponseDTO;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.modal.Content;
import com.robosoft.elearning.repository.ContentRepository;
import com.robosoft.elearning.repository.LessonRepository;
import com.robosoft.elearning.repository.UserLikedRepository;
import com.robosoft.elearning.services.ContentService;
import com.robosoft.elearning.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private UserLikedRepository userLikedRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ResponseUtil responseUtil;


    @Override
    public ResponseEntity<List<ContentResponseDTO>> getPaginatedContent(Long lessonId, int pageNumber, int pageSize) {
        // Validate pageNumber and pageSize
        if (pageNumber < 1 || pageSize < 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Adjust page number for zero-based indexing
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Content> contentPage = contentRepository.findByLessonId(lessonId, pageable);

        if (contentPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Map Content entities to ContentResponseDTO
        List<ContentResponseDTO> responseDTOList = contentPage.getContent().stream()
                .map(content -> new ContentResponseDTO(
                        content.getId(),
                        content.getContentType(),
                        content.getContentImg(),
                        content.getInfo()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> redirectToPage(Long lessonId, int pageNumber, int pageSize) {
        ResponseEntity<List<ContentResponseDTO>> paginatedResponse = getPaginatedContent(lessonId, pageNumber, pageSize);

        if (paginatedResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
            return new ResponseEntity<>("Invalid page number.", HttpStatus.BAD_REQUEST);
        }

        // Construct the redirect URL
        String redirectUrl = "/lesson/" + lessonId + "/page/" + pageNumber;
        return new ResponseEntity<>(redirectUrl, HttpStatus.OK);
    }



    public ResponseEntity<PaginatedContentResponseDTO> goToPage(Long lessonId, int pageNumber, int pageSize) {
        if (pageNumber < 1 || pageSize < 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Content> contentPage = contentRepository.findByLessonId(lessonId, pageable);

        if (contentPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<ContentResponseDTO> contentDTOs = contentPage.getContent()
                .stream()
                .map(content -> new ContentResponseDTO(content.getId(), content.getContentType()
                        , content.getContentImg(),content.getInfo()))
                .collect(Collectors.toList());

        PaginatedContentResponseDTO responseDTO = new PaginatedContentResponseDTO(
                contentDTOs,
                contentPage.getTotalPages(),
                contentPage.getNumber() + 1 // Convert 0-based to 1-based page number

        );

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


//    @Override
//    public ResponseEntity<ResponseDTO<LessonContentResponse>> getLessonContent(Long lessonId, int pageNumber, HttpServletRequest request) {
//        Long userId = jwtUtils.getUserIdFromRequestHeader(request);
//        if (userId == null) {
//            return responseUtil.errorResponse("User ID is missing in the request");
//        }
//
//        Lesson lesson = lessonRepository.findById(lessonId)
//                .orElseThrow(() -> new NotFoundException("Lesson not found"));
//
//        List<Content> contents = contentRepository.findByLessonIdOrderByPageNumber(lessonId);
//
//        if (contents.isEmpty()) {
//            return responseUtil.errorResponse("No content found for this lesson");
//        }
//
//        Content currentContent = contents.stream()
//                .filter(content -> content.getPageNumber() == pageNumber)
//                .findFirst()
//                .orElseThrow(() -> new NotFoundException("Page not found"));
//
//        boolean isLiked = userLikedRepository.existsByUserIdAndLessonId(userId, lessonId);
//
//        LessonContentResponse response = new LessonContentResponse();
//        response.setLessonName(lesson.getLessonName());
//        response.setContentType(currentContent.getContentType());
//        response.setContentUrl(currentContent.getInfo());
//        response.setAudioUrl(currentContent.getAudioUrl());
//        response.setCurrentPage(currentContent.getPageNumber());
//        response.setTotalPages(contents.size());
//        response.setLiked(isLiked);
//
//        List<PageNavigationResponse> pages = contents.stream()
//                .map(content -> {
//                    PageNavigationResponse page = new PageNavigationResponse();
//                    page.setPageNumber(content.getPageNumber());
//                    page.setPageLabel("Lesson " + content.getPageNumber() + ": " + lesson.getLessonName());
//                    return page;
//                })
//                .collect(Collectors.toList());
//
//        response.setPages(pages);
//
//        return responseUtil.successResponse(response);
//    }
}
