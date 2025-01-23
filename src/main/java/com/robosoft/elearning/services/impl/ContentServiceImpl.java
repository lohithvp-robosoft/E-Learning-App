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
    private JwtUtils jwtUtils;

    @Autowired
    private ResponseUtil responseUtil;


    @Override
    public ResponseEntity<List<ContentResponse>> getPaginatedContent(Long lessonId, int pageNumber, int pageSize) {
        if (pageNumber < 1 || pageSize < 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Content> contentPage = contentRepository.findByLessonId(lessonId, pageable);

        if (contentPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
        String redirectUrl = "/lesson/" + lessonId + "/page/" + pageNumber;
        return new ResponseEntity<>(redirectUrl, HttpStatus.OK);
    }


    public ResponseEntity<ResponseDTO<PaginatedContentResponseDTO>> goToPage(Long topicId, int pageNumber, int pageSize) {
        if (pageNumber < 1 || pageSize < 1) {
            return new ResponseEntity<>(
                    new ResponseDTO<>(400, HttpStatus.BAD_REQUEST.value(), "Invalid page number or size", null),
                    HttpStatus.BAD_REQUEST
            );
        }
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Content> contentPage = contentRepository.findByLessonId(topicId, pageable);
        if (contentPage.isEmpty()) {
            throw new NotFoundException("No content found for the given topic ID.");
        }
        Optional<Topic> topic = topicRepository.findById(topicId);
        if (!topic.isPresent()) {
            throw new NotFoundException("Topic not found for the given topic ID.");
        }

        String heading = topic.get().getHeading();
        Long lessonId = topic.get().getLessonId();
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
        ResponseDTO<PaginatedContentResponseDTO> response = new ResponseDTO<>(
                200,
                HttpStatus.OK.value(),
                "Content fetched successfully",
                responseDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
