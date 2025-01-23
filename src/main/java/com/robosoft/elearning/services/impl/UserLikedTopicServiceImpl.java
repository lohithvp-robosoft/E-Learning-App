package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.UserLikedTopicResponse;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.modal.*;
import com.robosoft.elearning.repository.ChapterRepository;
import com.robosoft.elearning.repository.TopicRepository;
import com.robosoft.elearning.repository.UserLikedTopicRepository;
import com.robosoft.elearning.repository.UserRepository;
import com.robosoft.elearning.services.UserLikedTopicServices;
import com.robosoft.elearning.util.EntityMapperUtil;
import com.robosoft.elearning.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserLikedTopicServiceImpl implements UserLikedTopicServices {

    @Autowired
    private UserLikedTopicRepository userLikedTopicRepository;

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

    @Override
    public ResponseEntity<ResponseDTO<Void>> toggleLike(Long topicId, HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);

        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new NotFoundException("Topic not found"));
        UserLikedTopic userLikedTopic = userLikedTopicRepository.findByUserAndTopic(user, topic);

        if (userLikedTopic != null) {
            userLikedTopicRepository.delete(userLikedTopic);

            return responseUtil.successResponse(null,"Successfully disliked the topic");
        } else {
            userLikedTopicRepository.save(new UserLikedTopic(user, topic));
            return responseUtil.successResponse(null,"Successfully disliked the topic");
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<List<UserLikedTopicResponse>>> getLikedTopics(HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);
        List<UserLikedTopic> likedTopics = userLikedTopicRepository.findByUser(user);

        List<Topic> topicList = likedTopics.stream().map(UserLikedTopic::getTopic).collect(Collectors.toList());
        List<UserLikedTopicResponse> userLikedTopicResponses = topicList.stream()
                .map(topic->{
                    Chapter chapter = topic.getLesson().getChapter();
                    Subject subject = chapter.getSubject();
                    int chapterIndex = chapterRepository.countBySubjectIdAndIdLessThan(subject.getId(), chapter.getId()) + 1;
                    return entityMapperUtil.convertToUserLikedTopicResponse(topic,chapterIndex);
                }).toList();

        return responseUtil.successResponse(userLikedTopicResponses);
    }
}
