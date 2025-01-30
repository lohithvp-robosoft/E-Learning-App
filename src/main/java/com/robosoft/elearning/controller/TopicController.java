package com.robosoft.elearning.controller;

import com.robosoft.elearning.dto.request.TopicRequest;
import com.robosoft.elearning.dto.response.ChapterLessonsResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.TopicResponse;
import com.robosoft.elearning.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org. springframework.data.domain.Pageable;
import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class TopicController {


    @Autowired
    private TopicService topicService;

    @GetMapping("/topics")
    public ResponseEntity<ResponseDTO<List<TopicResponse>>> getAllTopics() {
        return topicService.getAllTopics();
    }

    @GetMapping("/topics/{id}")
    public ResponseEntity<ResponseDTO<TopicResponse>> getTopicById(@PathVariable long id) {
        return topicService.getTopicById(id);
    }

    @GetMapping("/topics/chapter/{chapterId}/lessons/{lessonId}")
    public ResponseEntity<ResponseDTO<ChapterLessonsResponse>> getTopicsByChapterAndLesson(
            @PathVariable Long chapterId,
            @PathVariable Long lessonId) {
        return topicService.getTopicsByChapterAndLesson(chapterId, lessonId);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createTopic")
    public ResponseEntity<ResponseDTO<TopicResponse>> createTopic(@RequestBody TopicRequest topicRequest) {
        return topicService.createTopic(topicRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateTopic/{id}")
    public ResponseEntity<ResponseDTO<TopicResponse>> updateTopic(
            @PathVariable long id,
            @RequestBody TopicRequest topicRequest) {
        return topicService.updateTopic(id, topicRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteTopic/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteTopic(@PathVariable long id) {
        return topicService.deleteTopic(id);
    }
}