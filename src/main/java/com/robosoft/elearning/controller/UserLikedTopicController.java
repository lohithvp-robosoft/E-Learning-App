package com.robosoft.elearning.controller;

import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.UserLikedTopicResponse;
import com.robosoft.elearning.services.UserLikedTopicServices;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/liked-topics")
public class UserLikedTopicController {

    @Autowired
    private UserLikedTopicServices userLikedTopicServices;

    @GetMapping("/subjects/{subjectId}")
    public ResponseEntity<ResponseDTO<List<UserLikedTopicResponse>>> getLickedTopic(@PathVariable Long subjectId, HttpServletRequest request){
        return userLikedTopicServices.getLikedTopics(subjectId,request);
    }


    @PutMapping("/toggle/topic/{topicId}")
    public ResponseEntity<ResponseDTO<Void>> toggleLike(@PathVariable long topicId, @RequestParam int pageNo , HttpServletRequest request){
        return userLikedTopicServices.toggleLikeForPage(topicId,pageNo,request);
    }

}

