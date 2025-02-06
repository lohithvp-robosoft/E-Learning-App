package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.request.TopicRequest;
import com.robosoft.elearning.dto.response.ChapterLessonsResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.TopicResponse;
import com.robosoft.elearning.dto.response.TopicWithTopicsResponse;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.model.Chapter;
import com.robosoft.elearning.model.Lesson;
import com.robosoft.elearning.model.Topic;
import com.robosoft.elearning.repository.ChapterRepository;
import com.robosoft.elearning.repository.ContentRepository;
import com.robosoft.elearning.repository.LessonRepository;
import com.robosoft.elearning.repository.TopicRepository;
import com.robosoft.elearning.services.TopicService;
import com.robosoft.elearning.util.EntityMapperUtil;
import com.robosoft.elearning.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements TopicService {


    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private EntityMapperUtil entityMapperUtil;

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Value("${chapter.error.not-found}")
    private String chapterNotFoundMessage;

    @Value("${lesson.error.not-found}")
    private String lessonNotFoundMessage;

    @Value("${topic.error.not-found}")
    private String topicNotFoundMessage;

    @Override
    public ResponseEntity<ResponseDTO<List<TopicResponse>>> getAllTopics() {
        List<Topic> topics = topicRepository.findAll();
        List<TopicResponse> topicResponses = topics.stream()
                .map(entityMapperUtil::convertTopicToTopicResponse)
                .collect(Collectors.toList());
        return responseUtil.successResponse(topicResponses);
    }

    @Override
    public ResponseEntity<ResponseDTO<TopicResponse>> getTopicById(long id) {
        return topicRepository.findById(id)
                .map(topic -> {
                    TopicResponse topicResponse = entityMapperUtil.convertTopicToTopicResponse(topic);
                    return responseUtil.successResponse(topicResponse);
                })
                .orElse(responseUtil.errorResponse("Topic not found"));
    }

    public ResponseEntity<ResponseDTO<ChapterLessonsResponse>> getTopicsByChapterAndLesson(Long chapterId, Long lessonId) {

        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new NotFoundException("Chapter not found"));

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new NotFoundException("Lesson not found"));

        List<Topic> topics = topicRepository.findByLessonId(lessonId);
        topics.sort(Comparator.comparing(Topic::getId));

        List<TopicWithTopicsResponse> topicResponses = new ArrayList<>();

        int currentPage = 1;

        for (Topic topic : topics) {
            int pagesForThisTopic = topic.getPages();

            List<Integer> pageNumbers = new ArrayList<>();

            int currentTopicPage = 1;

            for (int i = 1; i <= pagesForThisTopic; i++) {
                pageNumbers.add(currentPage++);
                pageNumbers.add(currentTopicPage++);
            }

            topicResponses.add(new TopicWithTopicsResponse(
                    topic.getLevel(),
                    topic.getHeading(),
                    topic.getIcon(),
                    topic.getSubHeading(),
                    topic.getId(),
                    1,
                    pageNumbers
            ));
        }

        ChapterLessonsResponse chapterLessonResponse = new ChapterLessonsResponse(
                chapter.getId(),
                chapter.getChapterName(),
                lesson.getId(),
                lesson.getLessonName(),
                lessonId,
                topicResponses
        );

        return responseUtil.successResponse(chapterLessonResponse);
    }



    @Override
    public ResponseEntity<ResponseDTO<TopicResponse>> createTopic(TopicRequest topicRequest) {
        Lesson lesson = lessonRepository.findById(topicRequest.getLessonId())
                .orElseThrow(() -> new NotFoundException(lessonNotFoundMessage));

        Topic topic = new Topic();
        topic.setLesson(lesson);
        topic.setHeading(topicRequest.getHeading());
        topic.setSubHeading(topicRequest.getSubHeading());
        topic.setIcon(topicRequest.getIcon());
        topic.setLevel(topicRequest.getLevel());

        Topic savedTopic = topicRepository.save(topic);

        TopicResponse topicResponse = entityMapperUtil.convertTopicToTopicResponse(savedTopic);
        return responseUtil.successResponse(topicResponse);
    }

    @Override
    public ResponseEntity<ResponseDTO<TopicResponse>> updateTopic(long id, TopicRequest topicRequest) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(topicNotFoundMessage));

        if (!lessonRepository.existsById(topicRequest.getLessonId())) {
            throw new NotFoundException(lessonNotFoundMessage);
        }

        topic.setHeading(topicRequest.getHeading());
        topic.setSubHeading(topicRequest.getSubHeading());
        topic.setIcon(topicRequest.getIcon());
        topic.setLevel(topicRequest.getLevel());

        Topic updatedTopic = topicRepository.save(topic);

        TopicResponse topicResponse = entityMapperUtil.convertTopicToTopicResponse(updatedTopic);
        return responseUtil.successResponse(topicResponse);
    }

    @Override
    public ResponseEntity<ResponseDTO<Void>> deleteTopic(long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(topicNotFoundMessage));

        topicRepository.delete(topic);
        return responseUtil.successResponse(null);
    }

}