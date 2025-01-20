package com.robosoft.elearning.controller;

import com.robosoft.elearning.dto.response.TopicResponse;
import com.robosoft.elearning.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/v1/topic/{topicId}")
    public ResponseEntity<TopicResponse> getTopicById(@PathVariable long topicId) {
        return topicService.getTopicById(topicId);
    }
}
