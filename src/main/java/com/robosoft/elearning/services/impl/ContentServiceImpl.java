package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.request.ContentRequest;
import com.robosoft.elearning.dto.response.ContentResponse;
import com.robosoft.elearning.dto.response.PaginatedContentResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.model.*;
import com.robosoft.elearning.repository.ContentRepository;
import com.robosoft.elearning.repository.LessonRepository;
import com.robosoft.elearning.repository.TopicRepository;
import com.robosoft.elearning.repository.UserLikedPageRepository;
import com.robosoft.elearning.services.ContentService;
import com.robosoft.elearning.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${pageNo.error.invalid}")
    private String invalidPageNoMessage;

    @Value("${content.error.not-found}")
    private String contentNotFoundMessage;

    @Value("${topic.error.not-found}")
    private String topicNotFoundMessage;

    @Value("${lesson.error.not-found}")
    private String lessonNotFoundMessage;

    @Value("${chapter.error.not-found}")
    private String chapterNotFoundMessage;

    public ResponseEntity<ResponseDTO<PaginatedContentResponse>> goToPage(
            Long lessonId, Long topicId, int pageNumber, HttpServletRequest request) {

        User user = jwtUtils.getUserDataFromRequest(request);

        if (pageNumber < 1) {
            return responseUtil.errorResponse(invalidPageNoMessage, HttpStatus.BAD_REQUEST.value());
        }

        List<Content> contentList = contentRepository.findByTopicIdAndPageNumber(topicId, pageNumber);
        if (contentList.isEmpty()) {
            throw new NotFoundException(contentNotFoundMessage);
        }

        Optional<Topic> topicOptional = topicRepository.findById(topicId);
        if (!topicOptional.isPresent()) {
            throw new NotFoundException(topicNotFoundMessage);
        }
        Topic topic = topicOptional.get();
        Lesson lesson = topic.getLesson();
        if (lesson == null) {
            throw new NotFoundException(lessonNotFoundMessage);
        }

        Optional<Lesson> lessonOptional = lessonRepository.findById(lessonId);
        if (!lessonOptional.isPresent()) {
            throw new NotFoundException(lessonNotFoundMessage);
        }
        Chapter chapter = lesson.getChapter();
        if (chapter == null) {
            throw new NotFoundException(chapterNotFoundMessage);
        }

        List<Lesson> allLessons = lessonRepository.findByChapterId(chapter.getId());
        allLessons.sort(Comparator.comparing(Lesson::getId));
        int lessonIndex = allLessons.indexOf(lesson) + 1;

        List<Topic> topics = topicRepository.findByLessonId(lessonId);
        if (topics.isEmpty()) {
            throw new NotFoundException(topicNotFoundMessage);
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
        return responseUtil.successResponse(responseDTO, null);
    }



    @Override
    public ResponseEntity<ResponseDTO<String>> createContent(ContentRequest contentRequest) {
        Lesson lesson = lessonRepository.findById(contentRequest.getLessonId())
                .orElseThrow(() -> new NotFoundException(lessonNotFoundMessage));
        Topic topic = topicRepository.findById(contentRequest.getTopicId())
                .orElseThrow(() -> new NotFoundException(topicNotFoundMessage));
        Content content = new Content();
        content.setHeading(contentRequest.getHeading());
        content.setContentType(contentRequest.getContentType());
        content.setContentImg(contentRequest.getContentImg());
        content.setInfo(contentRequest.getInfo());

        contentRepository.save(content);
        return responseUtil.successResponse(null);
    }

    @Override
    public ResponseEntity<ResponseDTO<String>> updateContent(Long contentId, ContentRequest contentRequest) {

        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new NotFoundException(contentNotFoundMessage));
        content.setHeading(contentRequest.getHeading());
        content.setContentType(contentRequest.getContentType());
        content.setContentImg(contentRequest.getContentImg());
        content.setInfo(contentRequest.getInfo());

        contentRepository.save(content);
        return responseUtil.successResponse(null);
    }

    @Override
    public ResponseEntity<ResponseDTO<String>> deleteContent(Long contentId) {

        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new NotFoundException(contentNotFoundMessage));

        contentRepository.delete(content);
        return responseUtil.successResponse(null);
    }

}