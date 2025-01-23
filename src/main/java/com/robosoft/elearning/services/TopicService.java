package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.TopicResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TopicService {
    ResponseEntity<ResponseDTO<List<TopicResponse>>> getAllTopics();
    ResponseEntity<ResponseDTO<TopicResponse>> getTopicById(long id);
}