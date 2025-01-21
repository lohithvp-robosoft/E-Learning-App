package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.TopicResponse;
import com.robosoft.elearning.modal.Topic;
import com.robosoft.elearning.repository.TopicRepository;
import com.robosoft.elearning.services.TopicService;
import com.robosoft.elearning.util.EntityMapperUtil;
import com.robosoft.elearning.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements TopicService {


    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private EntityMapperUtil entityMapperUtil;

    @Autowired
    private ResponseUtil responseUtil;

    @Override
    public ResponseEntity<ResponseDTO<List<TopicResponse>>> getAllTopics() {
        List<Topic> topics = topicRepository.findAll();
        List<TopicResponse> topicResponses = topics.stream()
                .map(entityMapperUtil::convertTopicToTopicResponse)
                .collect(Collectors.toList());
        return responseUtil.successResponse(topicResponses);
    }

    @Override
    public ResponseEntity<ResponseDTO<TopicResponse>> getTopicById(long id) {
        return topicRepository.findById(id)
                .map(topic -> {
                    TopicResponse topicResponse = entityMapperUtil.convertTopicToTopicResponse(topic);
                    return responseUtil.successResponse(topicResponse);
                })
                .orElse(responseUtil.errorResponse("Topic not found"));
    }
}