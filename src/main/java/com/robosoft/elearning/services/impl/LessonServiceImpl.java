package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.request.LessonRequest;
import com.robosoft.elearning.dto.response.*;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.modal.*;
import com.robosoft.elearning.repository.*;
import com.robosoft.elearning.services.LessonService;
import com.robosoft.elearning.util.EntityMapperUtil;
import com.robosoft.elearning.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private EntityMapperUtil entityMapperUtil;

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserCurrentlyStudyingRepository userCurrentlyStudyingRepository;

    @Autowired
    private TopicCompletedRepository topicCompletedRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

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



    public ResponseEntity<ResponseDTO<List<CurrentlyStudyingLessonResponse1>>> getCurrentlyStudyingLessonByChapterId1(Long subjectId, HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);
        Long userId = user.getId();
        Optional<UserCurrentlyStudying> optionalUserCurrentlyStudying = userCurrentlyStudyingRepository.findByUserIdAndSubjectId(userId,subjectId);
        Subject currentSubject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new NotFoundException("Subject Not Found"));
        if (!optionalUserCurrentlyStudying.isPresent()) {
            System.out.println("Hello");
            Chapter firstChapter = currentSubject.getChapters().get(0);
            List<Lesson> lessonList = firstChapter.getLessons();

            List<CurrentlyStudyingLessonResponse1> currentlyStudyingLessonResponse1List = new ArrayList<>();
            Long lessonIndex = 0L;
            for (Lesson lesson : lessonList) {
                List<TopicWithTopicNameResponse> topicWithTopicNameResponses = new ArrayList<>();
                List<Topic> topicList = lesson.getTopics();
                for (Topic topic : topicList) {
                    TopicWithTopicNameResponse topicWithTopicNameResponse = new TopicWithTopicNameResponse(
                            topic.getId(),
                            topic.getLessonId(),
                            topic.getHeading(),
                            topic.getSubHeading(),
                            false,
                            topic.getLesson().getChapter().getSubject().getId()
                    );
                    topicWithTopicNameResponses.add(topicWithTopicNameResponse);
                }
                CurrentlyStudyingLessonResponse1 currentlyStudyingLessonResponse1 = new CurrentlyStudyingLessonResponse1(
                        lesson.getId(),
                        lesson.getLessonName(),
                        ++lessonIndex,
                        lesson.getChapter().getId(),
                        false,
                        0,
                        topicWithTopicNameResponses
                );
                currentlyStudyingLessonResponse1List.add(currentlyStudyingLessonResponse1);

            }
            return responseUtil.successResponse(currentlyStudyingLessonResponse1List);
        }

        UserCurrentlyStudying userCurrentlyStudying1 = optionalUserCurrentlyStudying.get();
        List<Lesson> lessons = userCurrentlyStudying1.getCurrentChapter().getLessons();
        List<CurrentlyStudyingLessonResponse1> currentlyStudyingLessonResponse1s = new ArrayList<>();

//        List<LessonWithTopicResponse> lessonWithTopicResponsesList = new ArrayList<>();

        Long lessonIndex = 0L;
        boolean isLessonCompleted = true;
        boolean userCurrentlyStudying = false;

        for (Lesson lesson : lessons) {
            List<TopicWithTopicNameResponse> topics = new ArrayList<>();
            List<Topic> topicList = topicRepository.findByLessonId(lesson.getId());

            topics = topicList.stream()
                    .map((topic) -> {
                        return new TopicWithTopicNameResponse(topic.getId(),
                                topic.getLessonId(),
                                topic.getHeading(),
                                topic.getSubHeading(),
                                topicCompletedRepository.existsByTopicIdAndUserId(topic.getId(), userId),
                                topic.getLesson().getChapter().getSubject().getId()
                        );
                    }).toList();

            if (userCurrentlyStudying1.getCurrentLesson().getId() == lesson.getId()) {
                isLessonCompleted = false;
                userCurrentlyStudying = true;
            }
            CurrentlyStudyingLessonResponse1 currentlyStudyingLessonResponse1 = new CurrentlyStudyingLessonResponse1(
                    lesson.getId(),
                    lesson.getLessonName(),
                    ++lessonIndex,
                    lesson.getChapter().getId(),
                    userCurrentlyStudying1.getCurrentLesson().getId() == lesson.getId(),
                    isLessonCompleted ? 100 : userCurrentlyStudying ? userCurrentlyStudying1.getCompletedLessonInPercentage() : null,
                    topics
            );
            currentlyStudyingLessonResponse1s.add(currentlyStudyingLessonResponse1);
        }
        return responseUtil.successResponse(currentlyStudyingLessonResponse1s);
    }



    private boolean checkTopicCompletion(Long userId, Topic topic) {
        return topicCompletedRepository.existsByTopicIdAndUserId(topic.getId(), userId);
    }

    private int getLessonCompletionPercentage(Long userId, Lesson lesson) {
        long totalTopics = lesson.getTopics().size();
        long completedTopics = topicCompletedRepository.countByLessonAndUserId(lesson, userId);
        return totalTopics > 0 ? (int) ((completedTopics * 100) / totalTopics) : 0;
    }









    @Override
    public ResponseEntity<ResponseDTO<LessonResponse>> createLesson(LessonRequest lessonRequest) {
        Lesson lesson = entityMapperUtil.convertLessonRequestToLesson(lessonRequest);
        Lesson savedLesson = lessonRepository.save(lesson);
        LessonResponse lessonResponse = entityMapperUtil.convertLessonToLessonResponse(savedLesson);
        return responseUtil.successResponse(lessonResponse, "Lesson created successfully");
    }

    @Override
    public ResponseEntity<ResponseDTO<LessonResponse>> updateLesson(long id, LessonRequest lessonRequest) {
        Optional<Lesson> existingLesson = lessonRepository.findById(id);
        if (existingLesson.isPresent()) {
            Lesson lesson = existingLesson.get();
            entityMapperUtil.updateLessonFromRequest(lessonRequest, lesson);
            Lesson updatedLesson = lessonRepository.save(lesson);
            LessonResponse lessonResponse = entityMapperUtil.convertLessonToLessonResponse(updatedLesson);
            return responseUtil.successResponse(lessonResponse, "Lesson updated successfully");
        } else {
            throw new NotFoundException("Lesson not found");
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<Void>> deleteLesson(long id) {
        Optional<Lesson> existingLesson = lessonRepository.findById(id);
        if (existingLesson.isPresent()) {
            lessonRepository.deleteById(id);
            return responseUtil.successResponse(null, "Lesson deleted successfully");
        } else {
            throw new NotFoundException("Lesson not found");
        }
    }


}