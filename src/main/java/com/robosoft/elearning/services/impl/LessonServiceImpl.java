package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.response.*;
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

//    public ResponseEntity<ResponseDTO<ChapterLessonsResponse>> getLessonsDetailsByChapterId(long chapterId) {
//        Chapter chapter = chapterRepository.findById(chapterId)
//                .orElseThrow(() -> new RuntimeException("Chapter not found"));
//
//        List<Lesson> lessons = lessonRepository.findByChapterId(chapterId);
//
//        List<LessonWithTopicResponse> lessonResponses = new ArrayList<>();
//
//        for (int i = 0; i < lessons.size(); i++) {
//            Lesson lesson = lessons.get(i);
//            List<TopicWithTopicNameResponse> topicResponses = new ArrayList<>();
//
//            for (Topic topic : lesson.getTopics()) {
//                topicResponses.add(new TopicWithTopicNameResponse(
//                        topic.getId(),
//                        lesson.getId(), // Lesson ID
//                        topic.getHeading(),
//                        topic.getSubHeading()
//                ));
//            }
//
//            lessonResponses.add(new LessonWithTopicResponse(
//                    chapterId, // Chapter ID
//                    (long) (i + 1), // Lesson Index (1-based index)
//                    lesson.getLessonName(),
//                    topicResponses
//            ));
//        }
//
//        ChapterLessonsResponse chapterLessonsResponse = new ChapterLessonsResponse(lessonResponses);
//        return responseUtil.successResponse(chapterLessonsResponse);
//    }

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

    public ResponseEntity<ResponseDTO<List<LessonWithTopicResponse>>> getLessonsDetails(HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);
        List<Lesson> lessons;
        Long activeChapterId = getActiveChapterId(user.getId());

        if (activeChapterId != null) {
            lessons = lessonRepository.findByChapterId(activeChapterId);
        } else {
            lessons = lessonRepository.findAll();
        }
        List<LessonWithTopicResponse> lessonResponses = lessons.stream().map(lesson -> {

            int lessonIndex = getLessonIndex(lesson.getChapter().getId(), lesson.getId())+1;
            List<TopicWithTopicNameResponse> topics = lesson.getTopics().stream()
                    .map(topic -> new TopicWithTopicNameResponse(
                            isTopicCompleted(topic),
                            topic.getSubHeading(),
                            topic.getHeading(),
                            topic.getLesson().getId(),
                            topic.getId()
                    )).collect(Collectors.toList());

            int totalTopics = topics.size();
            int completedTopics = (int) topics.stream().filter(TopicWithTopicNameResponse::isCompleted).count();
            int completedLessonInPercentage = totalTopics == 0 ? 0 : (completedTopics * 100) / totalTopics;
            return new LessonWithTopicResponse(
                    lesson.getId(),
                    lesson.getChapter() != null ? lesson.getChapter().getId() : null,
                    (long)lessonIndex,
                    lesson.getLessonName(),
                    completedLessonInPercentage,
                    topics
            );
        }).collect(Collectors.toList());

        return responseUtil.successResponse(lessonResponses, "Lessons fetched successfully");
    }


    private boolean isTopicCompleted(Topic topic) {
        User currentUser = getCurrentUser();
        Optional<TopicCompleted> topicCompleted = topicCompletedRepository.findByTopicIdAndUserId(topic.getId(), currentUser.getId());
        return topicCompleted.isPresent();
    }

    private User getCurrentUser() {
        return userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User not found")); // Modify this as needed
    }

    private Long getActiveChapterId(Long userId) {
        Optional<UserCurrentlyStudying> userCurrentlyStudying = userCurrentlyStudyingRepository.findByUserId(userId);
        if (userCurrentlyStudying.isPresent() && userCurrentlyStudying.get().getCurrentChapter() != null) {
            return userCurrentlyStudying.get().getCurrentChapter().getId();
        }
        return null;
    }

    public int getLessonIndex(Long chapterId, Long lessonId) {
        return lessonRepository.countByChapterIdAndIdLessThan(chapterId, lessonId);
    }

}