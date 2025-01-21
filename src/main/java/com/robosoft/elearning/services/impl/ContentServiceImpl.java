package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.response.LessonContentResponse;
import com.robosoft.elearning.dto.response.PageNavigationResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.modal.Content;
import com.robosoft.elearning.modal.Lesson;
import com.robosoft.elearning.repository.ContentRepository;
import com.robosoft.elearning.repository.LessonRepository;
import com.robosoft.elearning.repository.UserLikedRepository;
import com.robosoft.elearning.services.ContentService;
import com.robosoft.elearning.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<ResponseDTO<LessonContentResponse>> getLessonContent(Long lessonId, int pageNumber, HttpServletRequest request) {
        Long userId = jwtUtils.getUserIdFromRequestHeader(request);
        if (userId == null) {
            return responseUtil.errorResponse("User ID is missing in the request");
        }

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new NotFoundException("Lesson not found"));

        List<Content> contents = contentRepository.findByLessonIdOrderByPageNumber(lessonId);

        if (contents.isEmpty()) {
            return responseUtil.errorResponse("No content found for this lesson");
        }

        Content currentContent = contents.stream()
                .filter(content -> content.getPageNumber() == pageNumber)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Page not found"));

        boolean isLiked = userLikedRepository.existsByUserIdAndLessonId(userId, lessonId);

        LessonContentResponse response = new LessonContentResponse();
        response.setLessonName(lesson.getLessonName());
        response.setContentType(currentContent.getContentType());
        response.setContentUrl(currentContent.getInfo());
        response.setAudioUrl(currentContent.getAudioUrl());
        response.setCurrentPage(currentContent.getPageNumber());
        response.setTotalPages(contents.size());
        response.setLiked(isLiked);

        List<PageNavigationResponse> pages = contents.stream()
                .map(content -> {
                    PageNavigationResponse page = new PageNavigationResponse();
                    page.setPageNumber(content.getPageNumber());
                    page.setPageLabel("Lesson " + content.getPageNumber() + ": " + lesson.getLessonName());
                    return page;
                })
                .collect(Collectors.toList());

        response.setPages(pages);

        return responseUtil.successResponse(response);
    }
}
