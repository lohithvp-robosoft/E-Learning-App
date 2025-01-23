package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.response.*;
import com.robosoft.elearning.modal.Chapter;
import com.robosoft.elearning.modal.Lesson;
import com.robosoft.elearning.modal.Topic;
import com.robosoft.elearning.repository.ChapterRepository;
import com.robosoft.elearning.repository.LessonRepository;
import com.robosoft.elearning.services.LessonService;
import com.robosoft.elearning.util.EntityMapperUtil;
import com.robosoft.elearning.util.ResponseUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private EntityMapperUtil entityMapperUtil;

    @Autowired
    private ResponseUtil responseUtil;


    @Autowired
    private ChapterRepository chapterRepository;

    private final ChapterNameResponse chapterNameResponse;

    @Autowired
    public LessonServiceImpl(ChapterNameResponse chapterNameResponse) {
        this.chapterNameResponse = chapterNameResponse;
    }

    @Override
    public ResponseEntity<ResponseDTO<LessonResponse>> getLessonById(long id) {
        try {
            Lesson lesson = lessonRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Lesson not found"));
            LessonResponse lessonResponse = entityMapperUtil.convertLessonToLessonResponse(lesson);

            return responseUtil.successResponse(lessonResponse, "Lesson fetched successfully");
        } catch (RuntimeException ex) {
            return responseUtil.errorResponse(ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<LessonResponse>> getLessonDetailsById(long lessonId) {
        try {
            Lesson lesson = lessonRepository.findById(lessonId)
                    .orElseThrow(() -> new RuntimeException("Lesson not found"));
            LessonResponse lessonResponse = entityMapperUtil.convertLessonToLessonResponse(lesson);

            return responseUtil.successResponse(lessonResponse, "Lesson details fetched successfully");
        } catch (RuntimeException ex) {
            return responseUtil.errorResponse(ex.getMessage());
        }
    }

    @Transactional
    @Override
    public ResponseEntity<ResponseDTO<List<LessonResponse>>> getLessonsByChapterId(Long chapterId) {
        List<Lesson> lessons = lessonRepository.findByChapterId(chapterId);
        List<LessonResponse> responseList = new ArrayList<>();
        lessons.forEach(lesson -> responseList.add(entityMapperUtil.convertLessonToLessonResponse(lesson)));
        return responseUtil.successResponse(responseList);
    }

    public ResponseEntity<ResponseDTO<ChapterLessonsResponse>> getLessonsDetailsByChapterId(long chapterId) {
        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new RuntimeException("Chapter not found"));

        List<Lesson> lessons = lessonRepository.findByChapterId(chapterId);

        List<LessonWithTopicResponse> lessonResponses = new ArrayList<>();

        for (int i = 0; i < lessons.size(); i++) {
            Lesson lesson = lessons.get(i);
            List<TopicWithTopicNameResponse> topicResponses = new ArrayList<>();

            for (Topic topic : lesson.getTopics()) {
                topicResponses.add(new TopicWithTopicNameResponse(
                        topic.getId(),
                        lesson.getId(), // Lesson ID
                        topic.getHeading(),
                        topic.getSubHeading()
                ));
            }

            lessonResponses.add(new LessonWithTopicResponse(
                    chapterId, // Chapter ID
                    (long) (i + 1), // Lesson Index (1-based index)
                    lesson.getLessonName(),
                    topicResponses
            ));
        }

        ChapterLessonsResponse chapterLessonsResponse = new ChapterLessonsResponse(lessonResponses);
        return responseUtil.successResponse(chapterLessonsResponse);
    }

    public ResponseEntity<ResponseDTO<ChapterLessonTopicResponse>> getLessonsWithTopicsByChapterId(long chapterId) {
        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new RuntimeException("Chapter not found"));
        List<Lesson> lessons = lessonRepository.findByChapterId(chapterId);
        List<LessonResponse> lessonResponses = new ArrayList<>();
        for (Lesson lesson : lessons) {
            long lessonIndex = lessonRepository.countByChapterIdAndIdLessThan(chapterId, lesson.getId()) + 1;
            lessonResponses.add(new LessonResponse(
                    lessonIndex,
                    lesson.getLessonName(),
                    lesson.getLessonImg(),
                    lesson.getLevel(),
                    lesson.getHeading(),
                    lesson.getSubheading()
            ));
        }
        ChapterLessonTopicResponse chapterLessonTopicResponse = new ChapterLessonTopicResponse(
                chapter.getId(),
                chapter.getChapterName(),
                lessonResponses
        );
        return responseUtil.successResponse(chapterLessonTopicResponse);
    }

}