package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.UserLikedTopicResponse;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.modal.*;
import com.robosoft.elearning.repository.ChapterRepository;
import com.robosoft.elearning.repository.TopicRepository;
import com.robosoft.elearning.repository.UserLikedPageRepository;
import com.robosoft.elearning.repository.UserRepository;
import com.robosoft.elearning.services.UserLikedTopicServices;
import com.robosoft.elearning.util.EntityMapperUtil;
import com.robosoft.elearning.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserLikedTopicServiceImpl implements UserLikedTopicServices {

    @Autowired
    private UserLikedPageRepository userLikedPageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private EntityMapperUtil entityMapperUtil;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${topic.error.not-found}")
    private String topicNotFoundMessage;

    @Value("${likedPage.error.not-found}")
    private String likedPageNotFoundMessage;

    @Override
    public ResponseEntity<ResponseDTO<Void>> toggleLikeForPage(Long topicId, int pageNumber, HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);

        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new NotFoundException(topicNotFoundMessage));
        UserLikedPage userLikedPage = userLikedPageRepository.findByUserAndTopicAndPageNumber(user, topic, pageNumber);

        if (userLikedPage != null) {
            userLikedPageRepository.delete(userLikedPage);

            return responseUtil.successResponse(null);
        } else {
            userLikedPageRepository.save(new UserLikedPage(user, topic, pageNumber));
            return responseUtil.successResponse(null);
        }
    }


    @Override
    public ResponseEntity<ResponseDTO<List<UserLikedTopicResponse>>> getLikedTopics(Long subjectId, HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);
        List<UserLikedPage> likedPages = userLikedPageRepository.findByUser(user);

        if (likedPages.isEmpty()) {
            return responseUtil.errorResponse(likedPageNotFoundMessage);
        }

        List<UserLikedTopicResponse> userLikedTopicResponses = likedPages.stream()
                .filter(likedPage -> likedPage.getTopic().getLesson().getChapter().getSubject().getId().equals(subjectId))
                .map(likedPage -> {
                    Topic topic = likedPage.getTopic();
                    Lesson lesson = topic.getLesson();
                    Chapter chapter = lesson.getChapter();
                    Subject subject = chapter.getSubject();
                    List<Chapter> chapters = subject.getChapters().stream()
                            .sorted(Comparator.comparing(Chapter::getId))
                            .toList();
                    int chapterIndex = chapters.indexOf(chapter) + 1;

                    return entityMapperUtil.convertToUserLikedTopicResponse(topic, chapterIndex, likedPage.getPageNumber(), chapter.getId(), lesson.getId());
                })
                .toList();

        return responseUtil.successResponse(userLikedTopicResponses);
    }

}
