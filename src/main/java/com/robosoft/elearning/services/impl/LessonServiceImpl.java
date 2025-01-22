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


    @Override
    public ResponseEntity<ResponseDTO<ChapterNameResponse>> getChapterWithLesson(long chapterId, int lessonId) {
        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new RuntimeException("Chapter not found"));

        Lesson selectedLesson = null;
        if (lessonId > 0 && lessonId <= chapter.getLessons().size()) {
            selectedLesson = chapter.getLessons().get(lessonId - 1);
        } else {
            throw new RuntimeException("Lesson not found");
        }

        Long lessonIndex = (long) lessonId;
        List<TopicWithTopicNameResponse> topicResponses = new ArrayList<>();

        for (Topic topic : selectedLesson.getTopics()) {
            topicResponses.add(new TopicWithTopicNameResponse(
                    topic.getId(),
                    topic.getLesson().getChapter().getSubject().getId(),
                    topic.getHeading(),
                    topic.getSubHeading()
            ));
        }


        LessonWithTopicResponse lessonResponse = new LessonWithTopicResponse(
                lessonIndex,
                selectedLesson.getLessonName(),
                topicResponses
        );

        ChapterNameResponse chapterResponse = new ChapterNameResponse(
                chapter.getChapterName(),
                List.of(lessonResponse)
        );

        return responseUtil.successResponse(chapterResponse);
    }

    @Override
    public ResponseEntity<ResponseDTO<List<ChapterNameResponse>>> getAllChaptersWithLessons() {
        List<Chapter> chapters = chapterRepository.findAll();
        List<ChapterNameResponse> responseList = new ArrayList<>();

        chapters.forEach(chapter -> {
            List<LessonWithTopicResponse> lessonResponses = new ArrayList<>();
            int lessonCounter = 1;
            for (Lesson lesson : chapter.getLessons()) {
                Long lessonIndex = (long) lessonCounter++;
                List<TopicWithTopicNameResponse> topicResponses = new ArrayList<>();

                for (Topic topic : lesson.getTopics()) {
                    topicResponses.add(new TopicWithTopicNameResponse(
                            topic.getId(),
                            topic.getLesson().getChapter().getSubject().getId(),
                            topic.getHeading(),
                            topic.getSubHeading()
                    ));
                }

                lessonResponses.add(new LessonWithTopicResponse(
                        lessonIndex,
                        lesson.getLessonName(),
                        topicResponses
                ));
            }

            ChapterNameResponse chapterResponse = new ChapterNameResponse(
                    chapter.getChapterName(),
                    lessonResponses
            );
            responseList.add(chapterResponse);
        });

        return responseUtil.successResponse(responseList);
    }

}