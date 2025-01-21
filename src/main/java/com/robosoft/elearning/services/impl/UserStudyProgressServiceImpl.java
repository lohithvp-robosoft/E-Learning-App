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

        if (isTopicAlreadyCompleted(topicId, userId)) {
            return responseUtil.successResponse(null,"Topic already completed");
        }

        saveTopicCompletion(topicId, userId);

        Topic topic = fetchTopicById(topicId);
        Lesson lesson = topic.getLesson();

        if (areAllTopicsInLessonCompleted(lesson, userId)) {
            markLessonAsCompleted(lesson, userId);

            Chapter chapter = lesson.getChapter();
            if (areAllLessonsInChapterCompleted(chapter.getId(), userId)) {
                markChapterAsCompleted(chapter, userId);

                updateSubjectCompletionPercentage(chapter.getSubject(), userId);
            }
        }
        return responseUtil.successResponse(null);
    }

    private boolean isTopicAlreadyCompleted(Long topicId, long userId) {
        return topicCompletedRepository.existsByTopicIdAndUserId(topicId, userId);
    }

    private void saveTopicCompletion(Long topicId, long userId) {
        TopicCompleted topicCompleted = new TopicCompleted(topicId, userId);
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

    private void markLessonAsCompleted(Lesson lesson, long userId) {
        LessonCompleted lessonCompleted = new LessonCompleted(lesson.getId(), userId);
        lessonCompletedRepository.save(lessonCompleted);
    }

    private boolean areAllLessonsInChapterCompleted(Long chapterId, long userId) {
        long totalLessons = lessonRepository.countByChapterId(chapterId);
        long completedLessons = lessonCompletedRepository.countByChapterIdAndUserId(chapterId, userId);
        return completedLessons == totalLessons;
    }

    private void markChapterAsCompleted(Chapter chapter, long userId) {
        ChapterCompleted chapterCompleted = new ChapterCompleted(chapter.getId(), userId);
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
    public ResponseEntity<ResponseDTO<Void>> updateCurrentProgress(Long topicId, HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);

        UserCurrentlyStudying studyingSubject = userCurrentlyStudyingRepository
                .findByUserIdAndCurrentTopicId(user.getId(),topicId)
                .orElseGet(()->  new UserCurrentlyStudying(user));

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

            int completedPercentage = calculateSubjectCompletionPercentage(chapter.getId(), user.getId());
            studyingSubject.setCompletedChapterInPercentage(completedPercentage);

            userCurrentlyStudyingRepository.save(studyingSubject);
        }

        updateUserAverageCompletionPercentage(user);
        return responseUtil.successResponse(null);
    }

    @Override
    public ResponseEntity<ResponseDTO<List<UserCurrentlyStudyingResponse>>> getAllUserCurrentlyStudyingSubjects(HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);
        List<UserCurrentlyStudying> subjects = userCurrentlyStudyingRepository.findAllByUserId(user.getId());

        List<UserCurrentlyStudyingResponse> userCurrentlyStudyingResponses = entityMapperUtil.convertToUserCurrentlyStudyingResponseDTO(subjects);

        return responseUtil.successResponse(userCurrentlyStudyingResponses);
    }

    private int calculateSubjectCompletionPercentage(Long chapterId, Long userId) {
        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new NotFoundException("Chapter not found"));

        Subject subject = chapter.getSubject();

        long totalChapters = subject.getChapters().size();

        long completedChapters = chapterCompletedRepository.countBySubjectIdAndUserId(subject.getId(), userId);

        return (int) ((completedChapters * 100) / totalChapters);
    }


    private void updateUserAverageCompletionPercentage(User user) {
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
