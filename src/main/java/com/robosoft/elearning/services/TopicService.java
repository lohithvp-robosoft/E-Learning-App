package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.response.TopicResponse;
import org.springframework.http.ResponseEntity;

public interface TopicService {
    ResponseEntity<TopicResponse> getTopicById(long id);
}
