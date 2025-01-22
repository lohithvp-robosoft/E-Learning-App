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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private UserRepository userRepository;

    @Transactional
    @Override
    public ResponseEntity<ResponseDTO<Void>> markTopicAsCompleted(Long topicId, HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);
        long userId = user.getId();

        Topic topic = fetchTopicById(topicId);
        Lesson lesson = topic.getLesson();
        Chapter chapter = lesson.getChapter();
        Subject subject = chapter.getSubject();

        if (isTopicAlreadyCompleted(topicId, userId)) {
            return responseUtil.successResponse(null, "Topic already completed");
        }

        saveTopicCompletion(topicId, userId, lesson);


        if (areAllTopicsInLessonCompleted(lesson, userId)) {
            markLessonAsCompleted(lesson, userId, chapter);

            if (areAllLessonsInChapterCompleted(chapter.getId(), userId)) {
                markChapterAsCompleted(chapter, userId, subject);

                updateSubjectCompletionPercentage(chapter.getSubject(), userId);
            }
        }
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
                .orElseThrow(() -> new NotFoundException("Topic Not Found"));
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

    private void updateSubjectCompletionPercentage(Subject subject, long userId) {
        List<Chapter> totalChapters = subject.getChapters();
        long totalChapterCount = totalChapters.size();
        long completedChapters = chapterCompletedRepository.countBySubjectIdAndUserId(subject.getId(), userId);

        int completedPercentage = (int) ((completedChapters * 100) / totalChapterCount);

        UserCurrentlyStudying studyingSubject = userCurrentlyStudyingRepository
                .findByUserIdAndSubjectId(userId, subject.getId())
                .orElseThrow(() -> new NotFoundException("User Currently Studying Subject Not Found"));

        studyingSubject.setCompletedChapterInPercentage(completedPercentage);
        userCurrentlyStudyingRepository.save(studyingSubject);
    }

    @Transactional
    @Override
    public ResponseEntity<ResponseDTO<Void>> updateCurrentProgress(Long topicId, Long subjectId, HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);

        UserCurrentlyStudying studyingSubject = userCurrentlyStudyingRepository
                .findByUserIdAndSubjectId(user.getId(), subjectId)
                .orElseGet(() -> new UserCurrentlyStudying(user));

        if (topicId != null) {
            Topic topic = topicRepository.findById(topicId)
                    .orElseThrow(() -> new NotFoundException("Topic not found"));

            Lesson lesson = topic.getLesson();
            Chapter chapter = lesson.getChapter();
            Subject subject = chapter.getSubject();

            studyingSubject.setCurrentChapter(chapter);
            studyingSubject.setCurrentLesson(lesson);
            studyingSubject.setCurrentTopic(topic);
            studyingSubject.setSubject(subject);

            int completedChapterPercentage = calculateSubjectCompletionPercentage(chapter.getId(), user.getId());
            int completedLessonPercentage = calculateLessonCompletionPercentage(lesson, user.getId());
            System.out.println(completedLessonPercentage);
            studyingSubject.setCompletedChapterInPercentage(completedChapterPercentage);
            studyingSubject.setCompletedLessonInPercentage(completedLessonPercentage);

            userCurrentlyStudyingRepository.save(studyingSubject);
        }

        updateUserAverageChapterCompletion(user);
        return responseUtil.successResponse(null);
    }

    @Override
    public ResponseEntity<ResponseDTO<List<UserCurrentlyStudyingResponse>>> getAllUserCurrentlyStudying(HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);
        List<UserCurrentlyStudying> subjects = userCurrentlyStudyingRepository.findAllByUserId(user.getId());

        List<UserCurrentlyStudyingResponse> userCurrentlyStudyingResponses = entityMapperUtil.convertToUserCurrentlyStudyingResponseList(subjects);

        return responseUtil.successResponse(userCurrentlyStudyingResponses);
    }

    @Override
    public ResponseEntity<ResponseDTO<UserCurrentlyStudyingResponse>> getUserCurrentlyStudying(Long subjectId, HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);

        UserCurrentlyStudying userCurrentlyStudying = userCurrentlyStudyingRepository
                .findByUserIdAndSubjectId(user.getId(), subjectId)
                .orElseThrow(() -> new NotFoundException("User currently studying record not found for the specified subject"));

        UserCurrentlyStudyingResponse userCurrentlyStudyingResponse = entityMapperUtil.convertToUserCurrentlyStudyingResponse(userCurrentlyStudying);

        return responseUtil.successResponse(userCurrentlyStudyingResponse);
    }

    private int calculateSubjectCompletionPercentage(Long chapterId, Long userId) {
        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new NotFoundException("Chapter not found"));

        Subject subject = chapter.getSubject();

        long totalChapters = subject.getChapters().size();

        long completedChapters = chapterCompletedRepository.countBySubjectIdAndUserId(subject.getId(), userId);

        return (int) ((completedChapters * 100) / totalChapters);
    }

    private int calculateLessonCompletionPercentage(Lesson lesson, Long userId) {
        List<Topic> topics = lesson.getTopics();

        long completedTopicCount = topicCompletedRepository.countByLessonIdAndUserId(lesson.getId(), userId);

        int totalTopics = topics.size();
        if (totalTopics == 0) {
            return 0;
        }

        int completionPercentage = (int) ((completedTopicCount * 100.0) / totalTopics);
        return completionPercentage;
    }


    private void updateUserAverageChapterCompletion(User user) {
        List<UserCurrentlyStudying> studyingSubjects = userCurrentlyStudyingRepository
                .findAllByUserId(user.getId());

        int averagePercentage = (int) Math.round(studyingSubjects.stream()
                .mapToInt(UserCurrentlyStudying::getCompletedChapterInPercentage)
                .average()
                .orElse(0.0));

        user.setChaptersCompletedInPercentage(averagePercentage);
        userRepository.save(user);
    }
}
