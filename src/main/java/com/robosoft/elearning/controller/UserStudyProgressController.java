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
    public ResponseEntity<ResponseDTO<List<UserCurrentlyStudyingResponse>>> getUserCurrentlyStudyingBySubjectId(@PathVariable long subjectId, HttpServletRequest request){
        return userStudyProgressServices.getUserCurrentlyStudying(subjectId,request);
    }

    @PostMapping("/topic/{topicId}/completed")
    public ResponseEntity<ResponseDTO<Void>> markPageAsCompleted(
            @PathVariable Long topicId,
            @RequestParam int pageNumber,
            HttpServletRequest request
    ) {
        return userStudyProgressServices.markPageAsCompleted(topicId, pageNumber, request);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseDTO<List<UserCurrentlyStudyingResponse>>> searchBySubjectName(
            @RequestParam String subjectName, HttpServletRequest request) {
        return userStudyProgressServices.searchBySubjectName(subjectName, request);
    }

    @PostMapping("/viewed/topic/{topicId}")
    public ResponseEntity<ResponseDTO<Void>> markAsViewed(@PathVariable Long topicId, HttpServletRequest request){
        return userStudyProgressServices.markTopicAsViewed(topicId,request);
    }
}
