package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.UserCurrentlyStudyingResponse;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.modal.*;
import com.robosoft.elearning.repository.*;
import com.robosoft.elearning.services.UserStudyProgressServices;
import com.robosoft.elearning.util.EntityMapperUtil;
import com.robosoft.elearning.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserStudyProgressServiceImpl implements UserStudyProgressServices {

    @Autowired
    private TopicCompletedRepository topicCompletedRepository;

    @Autowired
    private LessonCompletedRepository lessonCompletedRepository;

    @Autowired
    private ChapterCompletedRepository chapterCompletedRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private UserCurrentlyStudyingRepository userCurrentlyStudyingRepository;

    @Autowired
    private EntityMapperUtil entityMapperUtil;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContentCompletionRepository contentCompletionRepository;

    @Value("${topic.error.not-found}")
    private String topicNotFoundMessage;

    @Value("${message.success.pageMarkedCompleted}")
    private String pageMarkedCompletedMessage;

    @Value("${message.success.pageAlreadyCompleted}")
    private String pageAlreadyCompletedMessage;

    @Value("${message.success.topic}")
    private String topicAlreadyCompletedMessage;

    @Value("${userProgress.error.not-found}")
    private String userProgressNotFoundMessage;

    @Transactional
    @Override
    public ResponseEntity<ResponseDTO<Void>> markPageAsCompleted(Long topicId, int pageNo, HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);
        long userId = user.getId();

        Topic topic = fetchTopicById(topicId);
        Lesson lesson = topic.getLesson();
        Chapter chapter = lesson.getChapter();
        Subject subject = chapter.getSubject();

        if (contentCompletionRepository.existsByTopicIdAndUserIdAndPageNumber(topicId, userId, pageNo)) {
            return responseUtil.successResponse(null, pageAlreadyCompletedMessage);
        }

        markPageAsCompletedForUser(topic, userId, pageNo);

        if (!areAllPagesInTopicCompleted(topic, userId)) {
            return responseUtil.successResponse(null, pageMarkedCompletedMessage);
        }

        if (isTopicAlreadyCompleted(topicId, userId)) {
            return responseUtil.successResponse(null, topicAlreadyCompletedMessage);
        }

        saveTopicCompletion(topicId, userId, lesson);

        if (areAllTopicsInLessonCompleted(lesson, userId)) {
            markLessonAsCompleted(lesson, userId, chapter);

            if (areAllLessonsInChapterCompleted(chapter.getId(), userId)) {
                markChapterAsCompleted(chapter, userId, subject);
            }
        }

        updateUserCurrentlyStudying(user, chapter, lesson, topic, subject);

        updateUserChapterCompletionPercentage(user);

        return responseUtil.successResponse(null);
    }

    private void markPageAsCompletedForUser(Topic topic, long userId, int pageNo) {
        ContentCompletion contentCompletion = new ContentCompletion(topic, userId, pageNo);
        contentCompletionRepository.save(contentCompletion);
    }

    private void updateUserCurrentlyStudying(User user, Chapter chapter, Lesson lesson, Topic topic, Subject subject) {
        UserCurrentlyStudying studyingSubject = userCurrentlyStudyingRepository
                .findByUserIdAndCurrentChapterId(user.getId(), chapter.getId())
                .orElseGet(() -> new UserCurrentlyStudying(user));

        float chapterPercentage = calculateChapterCompletionPercentage(studyingSubject, chapter, lesson, topic);
        studyingSubject.setCompletedChapterInPercentage(chapterPercentage);

        float completedLessonPercentage = calculateLessonCompletionPercentage(lesson, user.getId());
        studyingSubject.setCompletedLessonInPercentage(completedLessonPercentage);

        studyingSubject.setCurrentChapter(chapter);
        studyingSubject.setCurrentLesson(lesson);
        studyingSubject.setCurrentTopic(topic);
        studyingSubject.setSubject(subject);

        userCurrentlyStudyingRepository.save(studyingSubject);
    }

    private float calculateChapterCompletionPercentage(UserCurrentlyStudying studyingSubject, Chapter chapter, Lesson lesson, Topic topic) {
        int totalTopicsInLesson = lesson.getTopics().size();
        float completedTopicPerLesson = 1.0f / totalTopicsInLesson;
        int totalLessonsInChapter = chapter.getLessons().size();
        float completedTopicPerChapter = completedTopicPerLesson / totalLessonsInChapter;
        float completedTopicPerChapterInPercentage = completedTopicPerChapter * 100;

        return studyingSubject.getCompletedChapterInPercentage() + completedTopicPerChapterInPercentage > 100 ? 100f : studyingSubject.getCompletedChapterInPercentage() + completedTopicPerChapterInPercentage;
    }

    private void updateUserChapterCompletionPercentage(User user) {
        List<UserCurrentlyStudying> userCurrentlyStudyingList = userCurrentlyStudyingRepository.findAllByUserId(user.getId());
        if (userCurrentlyStudyingList.isEmpty()) {
            user.setChaptersCompletedInPercentage(0.0f);
        } else {
            int totalCompletedPercentage = 0;
            int noOfCurrentlyStudying = userCurrentlyStudyingList.size();
            for (UserCurrentlyStudying studying : userCurrentlyStudyingList) {
                totalCompletedPercentage += studying.getCompletedChapterInPercentage();
            }

            Float averageCompletedPercentage = (float) totalCompletedPercentage / noOfCurrentlyStudying;
            user.setChaptersCompletedInPercentage(averageCompletedPercentage);
            userRepository.save(user);
        }
    }

    private boolean areAllPagesInTopicCompleted(Topic topic, Long userId) {
        long totalPages = topic.getContents().stream().map(Content::getPageNumber).distinct().count();
        long completedPages = contentCompletionRepository.findCompletedPagesByTopicIdAndUserId(topic.getId(), userId).size();

        return completedPages == totalPages;
    }

    @Transactional
    @Override
    public ResponseEntity<ResponseDTO<Void>> markTopicAsViewed(Long topicId, HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);
        long userId = user.getId();

        Topic topic = fetchTopicById(topicId);
        Lesson lesson = topic.getLesson();
        Chapter chapter = lesson.getChapter();
        Subject subject = chapter.getSubject();

        boolean alreadyExists = userCurrentlyStudyingRepository.existsByUserIdAndCurrentChapterId(userId, chapter.getId());

        if (!alreadyExists) {
            UserCurrentlyStudying studyingSubject = new UserCurrentlyStudying(user);

            studyingSubject.setCurrentChapter(chapter);
            studyingSubject.setCurrentLesson(lesson);
            studyingSubject.setCurrentTopic(topic);
            studyingSubject.setSubject(subject);
            userCurrentlyStudyingRepository.save(studyingSubject);
        }

        List<UserCurrentlyStudying> userCurrentlyStudyingList = userCurrentlyStudyingRepository.findAllByUserId(userId);
        if (userCurrentlyStudyingList.isEmpty()) {
            user.setChaptersCompletedInPercentage(0.0f);
        } else {
            float totalCompletedPercentage = 0;
            int NoOfCurrentlyStudying = userCurrentlyStudyingList.size();
            for (UserCurrentlyStudying studying : userCurrentlyStudyingList) {
                totalCompletedPercentage += studying.getCompletedChapterInPercentage();
            }

            float averageCompletedPercentage = totalCompletedPercentage / NoOfCurrentlyStudying;
            user.setChaptersCompletedInPercentage(averageCompletedPercentage);
        }
        userRepository.save(user);

        return responseUtil.successResponse(null);
    }


    private boolean isTopicAlreadyCompleted(Long topicId, long userId) {
        return topicCompletedRepository.existsByTopicIdAndUserId(topicId, userId);
    }

    private void saveTopicCompletion(Long topicId, long userId, Lesson lesson) {
        TopicCompleted topicCompleted = new TopicCompleted(topicId, userId, lesson);
        topicCompletedRepository.save(topicCompleted);
    }

    private Topic fetchTopicById(Long topicId) {
        return topicRepository.findById(topicId)
                .orElseThrow(() -> new NotFoundException(topicNotFoundMessage));
    }

    private boolean areAllTopicsInLessonCompleted(Lesson lesson, long userId) {
        long totalTopics = topicRepository.countByLesson(lesson);
        long completedTopics = topicCompletedRepository.countByLessonIdAndUserId(lesson.getId(), userId);
        return completedTopics == totalTopics;
    }

    private void markLessonAsCompleted(Lesson lesson, long userId, Chapter chapter) {
        LessonCompleted lessonCompleted = new LessonCompleted(lesson.getId(), userId, chapter);
        lessonCompletedRepository.save(lessonCompleted);
    }

    private boolean areAllLessonsInChapterCompleted(Long chapterId, long userId) {
        long totalLessons = lessonRepository.countByChapterId(chapterId);
        long completedLessons = lessonCompletedRepository.countByChapterIdAndUserId(chapterId, userId);
        return completedLessons == totalLessons;
    }

    private void markChapterAsCompleted(Chapter chapter, long userId, Subject subject) {
        ChapterCompleted chapterCompleted = new ChapterCompleted(chapter.getId(), userId, subject);
        chapterCompletedRepository.save(chapterCompleted);
    }

    @Override
    public ResponseEntity<ResponseDTO<List<UserCurrentlyStudyingResponse>>> getAllUserCurrentlyStudying(HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);
        List<UserCurrentlyStudying> subjects = userCurrentlyStudyingRepository.findAllByUserId(user.getId());
        if (subjects.isEmpty()) {
            return responseUtil.errorResponse(userProgressNotFoundMessage);
        }
        List<UserCurrentlyStudyingResponse> userCurrentlyStudyingResponses = entityMapperUtil.convertToUserCurrentlyStudyingResponseList(subjects);

        if (userCurrentlyStudyingResponses.isEmpty()) throw new NotFoundException(userProgressNotFoundMessage);
        return responseUtil.successResponse(userCurrentlyStudyingResponses);
    }

    @Override
    public ResponseEntity<ResponseDTO<List<UserCurrentlyStudyingResponse>>> getUserCurrentlyStudying(Long subjectId, HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);

        List<UserCurrentlyStudying> userCurrentlyStudyingList = userCurrentlyStudyingRepository
                .findByUserIdAndSubjectId(user.getId(), subjectId);

        if (userCurrentlyStudyingList.isEmpty()) {
            throw new NotFoundException(userProgressNotFoundMessage);
        }

        List<UserCurrentlyStudyingResponse> userCurrentlyStudyingResponses = userCurrentlyStudyingList.stream()
                .map(entityMapperUtil::convertToUserCurrentlyStudyingResponse)
                .toList();

        return responseUtil.successResponse(userCurrentlyStudyingResponses);
    }

    @Override
    public ResponseEntity<ResponseDTO<List<UserCurrentlyStudyingResponse>>> searchBySubjectName(String subjectName, HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);

        List<UserCurrentlyStudying> studyingSubjects = userCurrentlyStudyingRepository
                .findAllByUserIdAndSubjectSubjectNameContainingIgnoreCase(user.getId(), subjectName);

        if (studyingSubjects.isEmpty()) {
            throw new NotFoundException(userProgressNotFoundMessage);
        }

        List<UserCurrentlyStudyingResponse> responses = entityMapperUtil.convertToUserCurrentlyStudyingResponseList(studyingSubjects);

        return responseUtil.successResponse(responses);
    }


    private float calculateLessonCompletionPercentage(Lesson lesson, Long userId) {
        List<Topic> topics = lesson.getTopics();

        int totalTopics = topics.size();
        if (totalTopics == 0) {
            return 0f;
        }

        long completedTopicCount = topicCompletedRepository.countByLessonIdAndUserId(lesson.getId(), userId);

        return (float) ((completedTopicCount * 100.0) / totalTopics);
    }
}
