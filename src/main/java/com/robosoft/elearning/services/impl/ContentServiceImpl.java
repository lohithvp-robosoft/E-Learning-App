package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.request.ContentRequest;
import com.robosoft.elearning.dto.response.ContentResponse;
import com.robosoft.elearning.dto.response.PaginatedContentResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.modal.*;
import com.robosoft.elearning.repository.ContentRepository;
import com.robosoft.elearning.repository.LessonRepository;
import com.robosoft.elearning.repository.TopicRepository;
import com.robosoft.elearning.repository.UserLikedPageRepository;
import com.robosoft.elearning.services.ContentService;
import com.robosoft.elearning.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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
    private UserLikedPageRepository userLikedPageRepository;


    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ResponseUtil responseUtil;

    //3rd trail
    public ResponseEntity<ResponseDTO<PaginatedContentResponse>> goToPage(
            Long lessonId, Long topicId, int pageNumber, HttpServletRequest request) {

        User user = jwtUtils.getUserDataFromRequest(request);

        if (pageNumber < 1) {
            return responseUtil.errorResponse("Invalid page number", HttpStatus.BAD_REQUEST.value());
        }

        List<Content> contentList = contentRepository.findByTopicIdAndPageNumber(topicId, pageNumber);
        if (contentList.isEmpty()) {
            throw new NotFoundException("No content found for the given topic ID and page number.");
        }

        Optional<Topic> topicOptional = topicRepository.findById(topicId);
        if (!topicOptional.isPresent()) {
            throw new NotFoundException("Topic not found for the given topic ID.");
        }
        Topic topic = topicOptional.get();
        Lesson lesson = topic.getLesson();
        if (lesson == null) {
            throw new NotFoundException("No lesson found for the given topic.");
        }

        Optional<Lesson> lessonOptional = lessonRepository.findById(lessonId);
        if (!lessonOptional.isPresent()) {
            throw new NotFoundException("Lesson not found for the given lesson ID.");
        }
        Chapter chapter = lesson.getChapter();
        if (chapter == null) {
            throw new NotFoundException("No chapter found for the given lesson.");
        }

        List<Lesson> allLessons = lessonRepository.findByChapterId(chapter.getId());
        allLessons.sort(Comparator.comparing(Lesson::getId));
        int lessonIndex = allLessons.indexOf(lesson) + 1;

        List<Topic> topics = topicRepository.findByLessonId(lessonId);
        if (topics.isEmpty()) {
            throw new NotFoundException("No topics found for the given lesson ID.");
        }


        boolean userLikedPage = userLikedPageRepository.existsByUserIdAndTopicIdAndPageNumber(user.getId(), topicId, pageNumber);


        List<ContentResponse> contentDTOs = contentList.stream()
                .map(content -> new ContentResponse(
                        content.getId(),
                        content.getHeading(),
                        content.getContentType(),
                        content.getContentImg(),
                        content.getInfo(),
                        content.getVideoUrl(),
                        content.getThumbnail(),
                        content.getAudioUrl(),
                        userLikedPage
                ))
                .collect(Collectors.toList());

        PaginatedContentResponse responseDTO = new PaginatedContentResponse(
                contentDTOs,
                (long) lessonIndex,
                (int) contentRepository.countTotalPagesByTopicId(topicId),
                pageNumber,
                lesson.getId(),
                lesson.getLessonName(),
                topicId
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