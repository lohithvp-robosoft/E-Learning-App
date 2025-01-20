package com.robosoft.elearning.controller;


import com.robosoft.elearning.dto.request.LikeLessonRequestDTO;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.services.UserLikedService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserLikedController {

    @Autowired
    private LikeLessonRequestDTO likeLessonRequestDTO;

    @Autowired
    private UserLikedService userLikedService;

    @PostMapping("/like-lesson")
    public ResponseEntity<String> likeLesson(@RequestBody LikeLessonRequestDTO requestDTO, HttpServletRequest request) {
        return userLikedService.likeLesson(requestDTO, request);
    }
}
