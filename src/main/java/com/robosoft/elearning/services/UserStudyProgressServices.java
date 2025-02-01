package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.UserCurrentlyStudyingResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserStudyProgressServices {

    ResponseEntity<ResponseDTO<Void>> markPageAsCompleted(Long topicId, int pageNumber, HttpServletRequest request);


    ResponseEntity<ResponseDTO<List<UserCurrentlyStudyingResponse>>> getAllUserCurrentlyStudying(HttpServletRequest request);

    ResponseEntity<ResponseDTO<List<UserCurrentlyStudyingResponse>>> getUserCurrentlyStudying(Long subjectId, HttpServletRequest request);

    ResponseEntity<ResponseDTO<List<UserCurrentlyStudyingResponse>>> searchBySubjectName(String subjectName, HttpServletRequest request);

    ResponseEntity<ResponseDTO<Void>> markTopicAsViewed(Long topicId, HttpServletRequest request);

}
