package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.UserLikedTopicResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserLikedTopicServices {
    ResponseEntity<ResponseDTO<Void>> toggleLikeForPage(Long topicId, int pageNumber, HttpServletRequest request);

    ResponseEntity<ResponseDTO<List<UserLikedTopicResponse>>> getLikedTopics(Long subjectId, HttpServletRequest request);
}
