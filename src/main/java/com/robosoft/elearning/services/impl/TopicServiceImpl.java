package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.response.TopicResponse;
import com.robosoft.elearning.modal.Topic;
import com.robosoft.elearning.repository.TopicRepository;
import com.robosoft.elearning.services.TopicService;
import com.robosoft.elearning.util.EntityMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private EntityMapperUtil entityMapperUtil;

    @Override
    public ResponseEntity<TopicResponse> getTopicById(long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found"));
        TopicResponse topicResponse = entityMapperUtil.convertTopicToTopicResponse(topic);
        return ResponseEntity.ok(topicResponse);
    }

}
