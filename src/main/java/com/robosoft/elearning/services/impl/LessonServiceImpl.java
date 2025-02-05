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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
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

    @Value("${lesson.error.not-found}")
    private String lessonNotFoundMessage;

    @Value("${chapter.error.not-found}")
    private String chapterNotFoundMessage;

    @Override
    public ResponseEntity<ResponseDTO<LessonResponse>> getLessonById(long id) {
        try {
            Lesson lesson = lessonRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(lessonNotFoundMessage));
            LessonResponse lessonResponse = entityMapperUtil.convertLessonToLessonResponse(lesson);

            return responseUtil.successResponse(lessonResponse, null);
        } catch (RuntimeException ex) {
            return responseUtil.errorResponse(ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<LessonResponse>> getLessonDetailsById(long lessonId) {
        try {
            Lesson lesson = lessonRepository.findById(lessonId)
                    .orElseThrow(() -> new NotFoundException(lessonNotFoundMessage));
            LessonResponse lessonResponse = entityMapperUtil.convertLessonToLessonResponse(lesson);

            return responseUtil.successResponse(lessonResponse, null);
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

    @Transactional
    @Override
    public ResponseEntity<ResponseDTO<List<CurrentlyStudyingLessonResponse>>> getCurrentlyStudyingLessonByChapterId(
            Long chapterId, HttpServletRequest request) {

        User user = jwtUtils.getUserDataFromRequest(request);
        Long userId = user.getId();

        Chapter currentChapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new NotFoundException(chapterNotFoundMessage));

        Optional<UserCurrentlyStudying> optionalUserCurrentlyStudying = userCurrentlyStudyingRepository
                .findByUserIdAndCurrentChapterId(userId, chapterId);

        if (!optionalUserCurrentlyStudying.isPresent()) {
            return responseUtil.successResponse(getLessonsForNewUser(currentChapter));
        }

        UserCurrentlyStudying userCurrentlyStudying = optionalUserCurrentlyStudying.get();
        return responseUtil.successResponse(getLessonsForExistingUser(currentChapter, userCurrentlyStudying, userId));
    }

    private List<CurrentlyStudyingLessonResponse> getLessonsForNewUser(Chapter currentChapter) {
        List<Lesson> lessonList = currentChapter.getLessons();
        List<CurrentlyStudyingLessonResponse> responseList = new ArrayList<>();
        Long lessonIndex = 0L;

        for (Lesson lesson : lessonList) {
            List<TopicWithTopicNameResponse> topics = getTopicResponseList(lesson.getTopics(), false);
            CurrentlyStudyingLessonResponse response = createLessonResponse(lesson, lessonIndex++, false, 0f, topics);
            responseList.add(response);
        }
        return responseList;
    }

    private List<CurrentlyStudyingLessonResponse> getLessonsForExistingUser(
            Chapter currentChapter, UserCurrentlyStudying userCurrentlyStudying, Long userId) {

        List<Lesson> lessons = currentChapter.getLessons();
        List<CurrentlyStudyingLessonResponse> responseList = new ArrayList<>();
        Long lessonIndex = 0L;
        boolean foundCurrentLesson = false;

        for (Lesson lesson : lessons) {
            List<TopicWithTopicNameResponse> topics;
            if (userCurrentlyStudying.getCurrentLesson().getId().equals(lesson.getId())) {
                foundCurrentLesson = true;
                topics = getTopicResponseListForCurrentLesson(lesson, userCurrentlyStudying, userId);
                responseList.add(createLessonResponse(lesson, lessonIndex++, true, userCurrentlyStudying.getCompletedLessonInPercentage(), topics));
            } else if (!foundCurrentLesson) {
                topics = getTopicResponseList(lesson.getTopics(), true);
                responseList.add(createLessonResponse(lesson, lessonIndex++, false, 100f, topics));
            } else {
                topics = getTopicResponseList(lesson.getTopics(), false);
                responseList.add(createLessonResponse(lesson, lessonIndex++, false, 0f, topics));
            }
        }
        return responseList;
    }

    private List<TopicWithTopicNameResponse> getTopicResponseList(List<Topic> topics, boolean completed) {
        return topics.stream()
                .map(topic -> new TopicWithTopicNameResponse(
                        topic.getId(),
                        topic.getLessonId(),
                        topic.getHeading(),
                        topic.getSubHeading(),
                        completed,
                        topic.getLesson().getChapter().getSubject().getId()
                ))
                .collect(Collectors.toList());
    }

    private List<TopicWithTopicNameResponse> getTopicResponseListForCurrentLesson(
            Lesson lesson, UserCurrentlyStudying userCurrentlyStudying, Long userId) {
        return lesson.getTopics().stream()
                .map(topic -> new TopicWithTopicNameResponse(
                        topic.getId(),
                        topic.getLessonId(),
                        topic.getHeading(),
                        topic.getSubHeading(),
                        topicCompletedRepository.existsByTopicIdAndUserId(topic.getId(), userId),
                        topic.getLesson().getChapter().getSubject().getId()
                ))
                .collect(Collectors.toList());
    }

    private CurrentlyStudyingLessonResponse createLessonResponse(
            Lesson lesson, Long lessonIndex, boolean isCurrent, float progress, List<TopicWithTopicNameResponse> topics) {

        return new CurrentlyStudyingLessonResponse(
                lesson.getId(),
                lesson.getLessonName(),
                lessonIndex,
                lesson.getChapter().getId(),
                isCurrent,
                progress,
                topics
        );
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
        return responseUtil.successResponse(lessonResponse);
    }

    @Override
    public ResponseEntity<ResponseDTO<LessonResponse>> updateLesson(long id, LessonRequest lessonRequest) {
        Optional<Lesson> existingLesson = lessonRepository.findById(id);
        if (existingLesson.isPresent()) {
            Lesson lesson = existingLesson.get();
            entityMapperUtil.updateLessonFromRequest(lessonRequest, lesson);
            Lesson updatedLesson = lessonRepository.save(lesson);
            LessonResponse lessonResponse = entityMapperUtil.convertLessonToLessonResponse(updatedLesson);
            return responseUtil.successResponse(lessonResponse);
        } else {
            throw new NotFoundException(lessonNotFoundMessage);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<Void>> deleteLesson(long id) {
        Optional<Lesson> existingLesson = lessonRepository.findById(id);
        if (existingLesson.isPresent()) {
            lessonRepository.deleteById(id);
            return responseUtil.successResponse(null);
        } else {
            throw new NotFoundException(lessonNotFoundMessage);
        }
    }

}