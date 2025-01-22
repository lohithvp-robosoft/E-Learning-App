package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.response.ContentResponse;
import com.robosoft.elearning.dto.response.PaginatedContentResponseDTO;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.modal.Content;
import com.robosoft.elearning.modal.Topic;
import com.robosoft.elearning.repository.ContentRepository;
import com.robosoft.elearning.repository.LessonRepository;
import com.robosoft.elearning.repository.TopicRepository;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserLikedRepository userLikedRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ResponseUtil responseUtil;


    @Override
    public ResponseEntity<List<ContentResponse>> getPaginatedContent(Long lessonId, int pageNumber, int pageSize) {
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

        // Map Content entities to ContentResponse
        List<ContentResponse> responseDTOList = contentPage.getContent().stream()
                .map(content -> new ContentResponse(
                        content.getId(),
                        content.getHeading(),
                        content.getContentType(),
                        content.getContentImg(),
                        content.getInfo()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> redirectToPage(Long lessonId, int pageNumber, int pageSize) {
        ResponseEntity<List<ContentResponse>> paginatedResponse = getPaginatedContent(lessonId, pageNumber, pageSize);

        if (paginatedResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
            return new ResponseEntity<>("Invalid page number.", HttpStatus.BAD_REQUEST);
        }

        // Construct the redirect URL
        String redirectUrl = "/lesson/" + lessonId + "/page/" + pageNumber;
        return new ResponseEntity<>(redirectUrl, HttpStatus.OK);
    }


    public ResponseEntity<ResponseDTO<PaginatedContentResponseDTO>> goToPage(Long topicId, int pageNumber, int pageSize) {
        // Validate page number and size
        if (pageNumber < 1 || pageSize < 1) {
            return new ResponseEntity<>(
                    new ResponseDTO<>(400, HttpStatus.BAD_REQUEST.value(), "Invalid page number or size", null),
                    HttpStatus.BAD_REQUEST
            );
        }

        // Create pageable object
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Content> contentPage = contentRepository.findByLessonId(topicId, pageable);

        // Check if content page is empty
        if (contentPage.isEmpty()) {
            throw new NotFoundException("No content found for the given topic ID.");
        }

        // Fetch topic details
        Optional<Topic> topic = topicRepository.findById(topicId);
        if (!topic.isPresent()) {
            throw new NotFoundException("Topic not found for the given topic ID.");
        }

        String heading = topic.get().getHeading(); // Assuming Topic entity has a `heading` field
        Long lessonId = topic.get().getLessonId();

        // Map content to DTOs
        List<ContentResponse> contentDTOs = contentPage.getContent()
                .stream()
                .map(content -> new ContentResponse(
                        content.getId(),
                        content.getHeading(),
                        content.getContentType(),
                        content.getContentImg(),
                        content.getInfo()
                ))
                .collect(Collectors.toList());

        // Create paginated response DTO
        PaginatedContentResponseDTO responseDTO = new PaginatedContentResponseDTO(
                contentDTOs,
                contentPage.getTotalPages(),
                contentPage.getNumber() + 1,
                lessonId,
                heading
        );

        // Wrap response DTO in ResponseDTO and return
        ResponseDTO<PaginatedContentResponseDTO> response = new ResponseDTO<>(
                200,
                HttpStatus.OK.value(),
                "Content fetched successfully",
                responseDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
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

