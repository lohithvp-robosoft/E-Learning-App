package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.UserCurrentlyStudyingResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserStudyProgressServices {

    ResponseEntity<ResponseDTO<Void>> markTopicAsCompleted(Long topicId, HttpServletRequest request);

    ResponseEntity<ResponseDTO<Void>>  updateCurrentProgress(Long topicId, HttpServletRequest request);

    ResponseEntity<ResponseDTO<List<UserCurrentlyStudyingResponse>>> getAllUserCurrentlyStudyingSubjects(HttpServletRequest request);
}
