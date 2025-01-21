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
    public ResponseEntity<ResponseDTO<List<UserCurrentlyStudyingResponse>>> getAllUserCurrentlyStudyingSubjects(HttpServletRequest request) {
        return userStudyProgressServices.getAllUserCurrentlyStudyingSubjects(request);
    }

    @PostMapping("/topic/completed")
    public ResponseEntity<ResponseDTO<Void>> markTopicAsCompleted(
            @RequestParam Long topicId,
            HttpServletRequest request
    ) {
        return userStudyProgressServices.markTopicAsCompleted(topicId, request);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO<Void>> updateCurrentProgress(
            @RequestParam Long topicId,
            HttpServletRequest request
    ) {
        return userStudyProgressServices.updateCurrentProgress(topicId, request);
    }
}
