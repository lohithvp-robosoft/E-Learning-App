package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.request.TopicRequest;
import com.robosoft.elearning.dto.response.ChapterLessonsResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.TopicResponse;
import com.robosoft.elearning.dto.response.TopicWithTopicsResponse;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.modal.Chapter;
import com.robosoft.elearning.modal.Lesson;
import com.robosoft.elearning.modal.Topic;
import com.robosoft.elearning.repository.ChapterRepository;
import com.robosoft.elearning.repository.ContentRepository;
import com.robosoft.elearning.repository.LessonRepository;
import com.robosoft.elearning.repository.TopicRepository;
import com.robosoft.elearning.services.TopicService;
import com.robosoft.elearning.util.EntityMapperUtil;
import com.robosoft.elearning.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
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



//
//    public ResponseEntity<ResponseDTO<ChapterLessonsResponse>> getTopicsByChapterAndLesson(Long chapterId, Long lessonId) {
//
//        Chapter chapter = chapterRepository.findById(chapterId)
//                .orElseThrow(() -> new NotFoundException("Chapter not found"));
//
//        Lesson lesson = lessonRepository.findById(lessonId)
//                .orElseThrow(() -> new NotFoundException("Lesson not found"));
//
//        List<Topic> topics = topicRepository.findByLessonId(lessonId);
//        List<TopicWithTopicsResponse> topicResponses = topics.stream()
//                .map(topic -> new TopicWithTopicsResponse(
//                        topic.getLevel(),
//                        topic.getHeading(),
//                        topic.getIcon(),
//                        topic.getSubHeading(),
//                        topic.getId(),
//                        topic.getPageStartsFrom()
//                ))
//                .collect(Collectors.toList());
//        ChapterLessonsResponse chapterLessonResponse = new ChapterLessonsResponse(
//                chapter.getId(),
//                chapter.getChapterName(),
//                lesson.getId(),
//                lesson.getLessonName(),
//                lessonId,
//                topicResponses
//        );
//        return responseUtil.successResponse(chapterLessonResponse);
//    }




    //2nd trail
//    public ResponseEntity<ResponseDTO<ChapterLessonsResponse>> getTopicsByChapterAndLesson(Long chapterId, Long lessonId) {
//
//        Chapter chapter = chapterRepository.findById(chapterId)
//                .orElseThrow(() -> new NotFoundException("Chapter not found"));
//
//        Lesson lesson = lessonRepository.findById(lessonId)
//                .orElseThrow(() -> new NotFoundException("Lesson not found"));
//
//        List<Topic> topics = topicRepository.findByLessonId(lessonId);
//        topics.sort(Comparator.comparing(Topic::getId));
//
//        int currentPage = 1;
//        List<TopicWithTopicsResponse> topicResponses = new ArrayList<>();
//
//        for (Topic topic : topics) {
//            topic.setPageStartsFrom(currentPage);
//            int pagesForThisTopic = 3;
//
//            topicResponses.add(new TopicWithTopicsResponse(
//                    topic.getLevel(),
//                    topic.getHeading(),
//                    topic.getIcon(),
//                    topic.getSubHeading(),
//                    topic.getId(),
//                    topic.getPageStartsFrom()
//            ));
//            currentPage += pagesForThisTopic;
//        }
//
//        ChapterLessonsResponse chapterLessonResponse = new ChapterLessonsResponse(
//                chapter.getId(),
//                chapter.getChapterName(),
//                lesson.getId(),
//                lesson.getLessonName(),
//                lessonId,
//
//                topicResponses
//        );
//
//        return responseUtil.successResponse(chapterLessonResponse);
//    }


    //3rd trail
    public ResponseEntity<ResponseDTO<ChapterLessonsResponse>> getTopicsByChapterAndLesson(Long chapterId, Long lessonId) {

        // Fetch chapter and lesson from repositories
        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new NotFoundException("Chapter not found"));

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new NotFoundException("Lesson not found"));

        // Get the list of topics for the given lesson
        List<Topic> topics = topicRepository.findByLessonId(lessonId);
        topics.sort(Comparator.comparing(Topic::getId));

        List<TopicWithTopicsResponse> topicResponses = new ArrayList<>();

        int currentPage = 1;

        // Iterate over each topic
        for (Topic topic : topics) {
            int pagesForThisTopic = topic.getPages();  // Fetch the number of pages for the topic

            // Create a list of page numbers for this topic
            List<Integer> pageNumbers = new ArrayList<>();
            for (int i = 1; i <= pagesForThisTopic; i++) {
                pageNumbers.add(currentPage++);  // Add the page number and increment for next topic
            }

            // Create the response object for this topic
            topicResponses.add(new TopicWithTopicsResponse(
                    topic.getLevel(),
                    topic.getHeading(),
                    topic.getIcon(),
                    topic.getSubHeading(),
                    topic.getId(),
                    1,  // Always starts from page 1 for each topic
                    pageNumbers  // List of page numbers for this topic
            ));
        }

        // Prepare the final response object with chapter and lesson details
        ChapterLessonsResponse chapterLessonResponse = new ChapterLessonsResponse(
                chapter.getId(),
                chapter.getChapterName(),
                lesson.getId(),
                lesson.getLessonName(),
                lessonId,
                topicResponses
        );

        // Return the response
        return responseUtil.successResponse(chapterLessonResponse);
    }





    @Override
    public ResponseEntity<ResponseDTO<TopicResponse>> createTopic(TopicRequest topicRequest) {
        Lesson lesson = lessonRepository.findById(topicRequest.getLessonId())
                .orElseThrow(() -> new NotFoundException("Lesson not found"));

        Topic topic = new Topic();
        topic.setLesson(lesson);
        topic.setHeading(topicRequest.getHeading());
        topic.setSubHeading(topicRequest.getSubHeading());
        topic.setIcon(topicRequest.getIcon());
        topic.setLevel(topicRequest.getLevel());

        Topic savedTopic = topicRepository.save(topic);

        TopicResponse topicResponse = entityMapperUtil.convertTopicToTopicResponse(savedTopic);
        return responseUtil.successResponse(topicResponse, "Topic created successfully");
    }

    @Override
    public ResponseEntity<ResponseDTO<TopicResponse>> updateTopic(long id, TopicRequest topicRequest) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Topic not found"));

        if (!lessonRepository.existsById(topicRequest.getLessonId())) {
            throw new NotFoundException("Lesson not found");
        }

        topic.setHeading(topicRequest.getHeading());
        topic.setSubHeading(topicRequest.getSubHeading());
        topic.setIcon(topicRequest.getIcon());
        topic.setLevel(topicRequest.getLevel());

        Topic updatedTopic = topicRepository.save(topic);

        TopicResponse topicResponse = entityMapperUtil.convertTopicToTopicResponse(updatedTopic);
        return responseUtil.successResponse(topicResponse, "Topic updated successfully");
    }

    @Override
    public ResponseEntity<ResponseDTO<Void>> deleteTopic(long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Topic not found"));

        topicRepository.delete(topic);
        return responseUtil.successResponse(null, "Topic deleted successfully");
    }

}