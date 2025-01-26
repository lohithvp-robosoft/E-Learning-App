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

import java.util.Comparator;
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
            return responseUtil.successResponse(null,"Successfully liked the topic");
        }
    }


    @Override
    public ResponseEntity<ResponseDTO<List<UserLikedTopicResponse>>> getLikedTopics(Long subjectId, HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);
        List<UserLikedTopic> likedTopics = userLikedTopicRepository.findByUser(user);

        if (likedTopics.isEmpty()) {
            return responseUtil.errorResponse("No liked topic found");
        }

        List<Topic> topicList = likedTopics.stream()
                .map(UserLikedTopic::getTopic)
                .filter(topic -> topic.getLesson().getChapter().getSubject().getId().equals(subjectId))
                .toList();

        if (topicList.isEmpty()) {
            return responseUtil.errorResponse("No liked topics found for the specified subject");
        }

        List<UserLikedTopicResponse> userLikedTopicResponses = topicList.stream()
                .map(topic -> {
                    Chapter chapter = topic.getLesson().getChapter();
                    Subject subject = chapter.getSubject();
                    List<Chapter> chapters = subject.getChapters().stream()
                            .sorted(Comparator.comparing(Chapter::getId))
                            .toList();
                    int chapterIndex = 0;
                    for (int i = 0; i < chapters.size(); i++) {
                        if (chapters.get(i).getId().equals(chapter.getId())) {
                            chapterIndex = i + 1;
                            break;
                        }
                    }
                    return entityMapperUtil.convertToUserLikedTopicResponse(topic, chapterIndex);
                })
                .toList();

        return responseUtil.successResponse(userLikedTopicResponses);
    }


//    @Override
//    public ResponseEntity<ResponseDTO<List<UserLikedTopicResponse>>> getLikedTopics(HttpServletRequest request) {
//        User user = jwtUtils.getUserDataFromRequest(request);
//        List<UserLikedTopic> likedTopics = userLikedTopicRepository.findByUser(user);
//
//        if(likedTopics.isEmpty()){
//            return  responseUtil.errorResponse("No liked topic found");
//        }
//
//        List<Topic> topicList = likedTopics.stream()
//                .map(UserLikedTopic::getTopic)
//                .toList();
//
//        List<UserLikedTopicResponse> userLikedTopicResponses = topicList.stream()
//                .map(topic -> {
//                    Chapter chapter = topic.getLesson().getChapter();
//                    Subject subject = chapter.getSubject();
//                    List<Chapter> chapters = subject.getChapters().stream()
//                            .sorted(Comparator.comparing(Chapter::getId))
//                            .toList();
//                    int chapterIndex = 0;
//                    for (int i = 0; i < chapters.size(); i++) {
//                        if (chapters.get(i).getId().equals(chapter.getId())) {
//                            chapterIndex = i + 1;
//                            break;
//                        }
//                    }
//                    return entityMapperUtil.convertToUserLikedTopicResponse(topic, chapterIndex);
//                })
//                .toList();
//
//        return responseUtil.successResponse(userLikedTopicResponses);
//    }

}
