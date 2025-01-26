package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.request.TopicRequest;
import com.robosoft.elearning.dto.response.ChapterLessonsResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.TopicResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TopicService {
    ResponseEntity<ResponseDTO<List<TopicResponse>>> getAllTopics();
    ResponseEntity<ResponseDTO<TopicResponse>> getTopicById(long id);
    ResponseEntity<ResponseDTO<ChapterLessonsResponse>> getTopicsByChapterAndLesson(Long chapterId, Long lessonId);
    ResponseEntity<ResponseDTO<TopicResponse>> createTopic(TopicRequest topicRequest);
    ResponseEntity<ResponseDTO<TopicResponse>> updateTopic(long id, TopicRequest topicRequest);
    ResponseEntity<ResponseDTO<Void>> deleteTopic(long id);

}