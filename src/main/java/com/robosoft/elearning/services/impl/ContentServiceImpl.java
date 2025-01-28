package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.request.ContentRequest;
import com.robosoft.elearning.dto.response.ContentResponse;
import com.robosoft.elearning.dto.response.PaginatedContentResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.modal.Content;
import com.robosoft.elearning.modal.Lesson;
import com.robosoft.elearning.modal.Topic;
import com.robosoft.elearning.modal.User;
import com.robosoft.elearning.repository.ContentRepository;
import com.robosoft.elearning.repository.LessonRepository;
import com.robosoft.elearning.repository.TopicRepository;
import com.robosoft.elearning.repository.UserLikedTopicRepository;
import com.robosoft.elearning.services.ContentService;
import com.robosoft.elearning.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.checkerframework.checker.units.qual.A;
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
    private UserLikedTopicRepository userLikedTopicRepository;


    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ResponseUtil responseUtil;


    public ResponseEntity<ResponseDTO<PaginatedContentResponse>> goToPage(Long lessonId, Long topicId, int pageNumber, HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);
        final int DEFAULT_PAGE_SIZE = 10;
        if (pageNumber < 1) {
            return responseUtil.errorResponse("Invalid page number", HttpStatus.BAD_REQUEST.value());
        }

        Pageable pageable = PageRequest.of(pageNumber - 1, DEFAULT_PAGE_SIZE);
        Page<Content> contentPage = contentRepository.findByLessonIdAndTopicId(lessonId, topicId, pageable);
        if (contentPage.isEmpty()) {
            throw new NotFoundException("No content found for the given lesson ID and topic ID.");
        }

        Optional<Lesson> lessonOptional = lessonRepository.findById(lessonId);
        if (!lessonOptional.isPresent()) {
            throw new NotFoundException("Lesson not found for the given lesson ID.");
        }
        Lesson lesson = lessonOptional.get();
        Optional<Topic> topicOptional = topicRepository.findById(topicId);
        if (!topicOptional.isPresent()) {
            throw new NotFoundException("Topic not found for the given topic ID.");
        }
        Topic topic = topicOptional.get();
        List<ContentResponse> contentDTOs = contentPage.getContent()
                .stream()
                .map(content -> {
                    boolean userLiked = userLikedTopicRepository.existsByUserIdAndTopicId(user.getId(), topic.getId());
                    return new ContentResponse(
                            content.getId(),
                            content.getHeading(),
                            content.getContentType(),
                            content.getContentImg(),
                            content.getInfo(),
                            userLiked
                    );
                })
                .collect(Collectors.toList());
        PaginatedContentResponse responseDTO = new PaginatedContentResponse(
                contentDTOs,
                contentPage.getTotalPages(),
                contentPage.getNumber() + 1,
                lesson.getId(),
                topic.getHeading(),
                topic.getId()
        );
        return responseUtil.successResponse(responseDTO, "Content fetched successfully");
    }

    @Override
    public ResponseEntity<ResponseDTO<String>> createContent(ContentRequest contentRequest) {
        Lesson lesson = lessonRepository.findById(contentRequest.getLessonId())
                .orElseThrow(() -> new NotFoundException("Lesson not found for the given ID."));
        Topic topic = topicRepository.findById(contentRequest.getTopicId())
                .orElseThrow(() -> new NotFoundException("Topic not found for the given ID."));
        Content content = new Content();
        content.setHeading(contentRequest.getHeading());
        content.setContentType(contentRequest.getContentType());
        content.setContentImg(contentRequest.getContentImg());
        content.setInfo(contentRequest.getInfo());

        contentRepository.save(content);
        return responseUtil.successResponse("Content created successfully");
    }

    @Override
    public ResponseEntity<ResponseDTO<String>> updateContent(Long contentId, ContentRequest contentRequest) {

        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new NotFoundException("Content not found for the given ID."));
        content.setHeading(contentRequest.getHeading());
        content.setContentType(contentRequest.getContentType());
        content.setContentImg(contentRequest.getContentImg());
        content.setInfo(contentRequest.getInfo());

        contentRepository.save(content);
        return responseUtil.successResponse("Content updated successfully");
    }

    @Override
    public ResponseEntity<ResponseDTO<String>> deleteContent(Long contentId) {

        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new NotFoundException("Content not found for the given ID."));

        contentRepository.delete(content);
        return responseUtil.successResponse("Content deleted successfully");
    }


}