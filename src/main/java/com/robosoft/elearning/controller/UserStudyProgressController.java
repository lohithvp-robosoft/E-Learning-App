package com.robosoft.elearning.controller;

import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.UserCurrentlyStudyingResponse;
import com.robosoft.elearning.services.UserStudyProgressServices;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/study-progress")
public class UserStudyProgressController {

    @Autowired
    private UserStudyProgressServices userStudyProgressServices;

    @GetMapping
    public ResponseEntity<ResponseDTO<List<UserCurrentlyStudyingResponse>>> getAllUserCurrentlyStudying(HttpServletRequest request) {
        return userStudyProgressServices.getAllUserCurrentlyStudying(request);
    }

    @GetMapping("/subjects/{subjectId}")
    public ResponseEntity<ResponseDTO<UserCurrentlyStudyingResponse>> getUserCurrentlyStudyingBySubjectId(@PathVariable long subjectId, HttpServletRequest request){
        return userStudyProgressServices.getUserCurrentlyStudying(subjectId,request);
    }

    @PostMapping("/topic/{topicId}/completed")
    public ResponseEntity<ResponseDTO<Void>> markTopicAsCompleted(
            @PathVariable Long topicId,
            HttpServletRequest request
    ) {
        return userStudyProgressServices.markTopicAsCompleted(topicId, request);
    }

    @PutMapping("/subject/{subjectId}/topic/{topicId}/update")
    public ResponseEntity<ResponseDTO<Void>> updateCurrentProgress(
            @PathVariable Long subjectId,
            @PathVariable Long topicId,
            HttpServletRequest request
    ) {
        return userStudyProgressServices.updateCurrentProgress(topicId, subjectId, request);
    }
}
