package com.robosoft.elearning.controller;

import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.TopicResponse;
import com.robosoft.elearning.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class TopicController {


    @Autowired
    private TopicService topicService;

    // Endpoint to get all topics
    @GetMapping("/v1/topics")
    public ResponseEntity<ResponseDTO<List<TopicResponse>>> getAllTopics() {
        return topicService.getAllTopics();
    }

    // Endpoint to get topic by ID
    @GetMapping("v1/topics/{id}")
    public ResponseEntity<ResponseDTO<TopicResponse>> getTopicById(@PathVariable long id) {
        return topicService.getTopicById(id);
    }
}
