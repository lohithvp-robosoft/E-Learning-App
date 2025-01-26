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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

//    ========================================================================
//    @Transactional
//    @Override
//    public ResponseEntity<ResponseDTO<Void>> markTopicAsCompleted(Long topicId, HttpServletRequest request) {
//        User user = jwtUtils.getUserDataFromRequest(request);
//        long userId = user.getId();
//
//        Topic topic = fetchTopicById(topicId);
//        Lesson lesson = topic.getLesson();
//        Chapter chapter = lesson.getChapter();
//        Subject subject = chapter.getSubject();
//
//        if (isTopicAlreadyCompleted(topicId, userId)) {
//            return responseUtil.successResponse(null, "Topic already completed");
//        }
//
//        saveTopicCompletion(topicId, userId, lesson);
//
//
//        if (areAllTopicsInLessonCompleted(lesson, userId)) {
//            markLessonAsCompleted(lesson, userId, chapter);
//
//            if (areAllLessonsInChapterCompleted(chapter.getId(), userId)) {
//                markChapterAsCompleted(chapter, userId, subject);
//
//                updateSubjectCompletionPercentage(chapter.getSubject(), userId);
//            }
//        }
//        return responseUtil.successResponse(null);
//    }
//====================================================


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


        boolean isChapterCompleted = false;
        if (areAllTopicsInLessonCompleted(lesson, userId)) {
            markLessonAsCompleted(lesson, userId, chapter);

            if (areAllLessonsInChapterCompleted(chapter.getId(), userId)) {
                markChapterAsCompleted(chapter, userId, subject);
                isChapterCompleted = true;
//                updateSubjectCompletionPercentage(chapter.getSubject(), userId);
            }
        }

        UserCurrentlyStudying studyingSubject = userCurrentlyStudyingRepository
                .findByUserIdAndCurrentChapterId(user.getId(), chapter.getId())
                .orElseGet(() -> new UserCurrentlyStudying(user));

        int currentTotalChapters = userCurrentlyStudyingRepository.countByUserId(userId);
        int previousTotalChapters = currentTotalChapters;
        if (studyingSubject.getCompletedChapterInPercentage() == 0) {
            previousTotalChapters--;
        }

        int previousCompletedChapters = (int) Math.round((user.getChaptersCompletedInPercentage() / 100.0) * previousTotalChapters);

        if (isChapterCompleted) {
            previousCompletedChapters++;
        }
        float updatedCompletionPercentage = ((float) previousCompletedChapters / currentTotalChapters) * 100;
        user.setChaptersCompletedInPercentage((int) updatedCompletionPercentage);

        int totalTopicsInLesson = lesson.getTopics().size();
        float completedTopicPerLesson = 1.0f / totalTopicsInLesson;
        int totalLessonsInChapter = chapter.getLessons().size();
        float completedTopicPerChapter = completedTopicPerLesson /totalLessonsInChapter;
        float completedTopicPerChapterInPercentage =  (completedTopicPerChapter * 100);
        float chapterPercentage = studyingSubject.getCompletedChapterInPercentage()+completedTopicPerChapterInPercentage;
        studyingSubject.setCompletedChapterInPercentage(chapterPercentage > 100 ? 100f : chapterPercentage);
        float completedLessonPercentage = calculateLessonCompletionPercentage(lesson, userId);
        studyingSubject.setCompletedLessonInPercentage(completedLessonPercentage);

        studyingSubject.setCurrentChapter(chapter);
        studyingSubject.setCurrentLesson(lesson);
        studyingSubject.setCurrentTopic(topic);
        studyingSubject.setSubject(subject);


        userRepository.save(user);
        userCurrentlyStudyingRepository.save(studyingSubject);


        return responseUtil.successResponse(null);
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

        if (alreadyExists) {
            return responseUtil.successResponse(null, "User is already studying this topic.");
        }

        UserCurrentlyStudying studyingSubject = new UserCurrentlyStudying(user);

        studyingSubject.setCurrentChapter(chapter);
        studyingSubject.setCurrentLesson(lesson);
        studyingSubject.setCurrentTopic(topic);
        studyingSubject.setSubject(subject);

        userCurrentlyStudyingRepository.save(studyingSubject);

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

//    private void updateSubjectCompletionPercentage(Subject subject, long userId) {
//        List<Chapter> totalChapters = subject.getChapters();
//        long totalChapterCount = totalChapters.size();
//        long completedChapters = chapterCompletedRepository.countBySubjectIdAndUserId(subject.getId(), userId);
//
//        float completedPercentage = (float) ((completedChapters * 100) / totalChapterCount);
//
//        UserCurrentlyStudying studyingSubject = userCurrentlyStudyingRepository
//                .findByUserIdAndSubjectId(userId, subject.getId())
//                .orElseThrow(() -> new NotFoundException("User Currently Studying Subject Not Found"));
//
//        studyingSubject.setCompletedChapterInPercentage(completedPercentage);
//        userCurrentlyStudyingRepository.save(studyingSubject);
//    }

//    @Transactional
//    @Override
//    public ResponseEntity<ResponseDTO<Void>> updateCurrentProgress(Long topicId, Long subjectId, HttpServletRequest request) {
//        User user = jwtUtils.getUserDataFromRequest(request);
//
////        System.out.println("Hello");
//        UserCurrentlyStudying studyingSubject = userCurrentlyStudyingRepository
//                .findByUserIdAndSubjectId(user.getId(), subjectId)
//                .orElseGet(() -> new UserCurrentlyStudying(user));
//
//        if (topicId != null) {
//            Topic topic = topicRepository.findById(topicId)
//                    .orElseThrow(() -> new NotFoundException("Topic not found"));
//
//            Lesson lesson = topic.getLesson();
//            Chapter chapter = lesson.getChapter();
//            Subject subject = chapter.getSubject();
//
//            studyingSubject.setCurrentChapter(chapter);
//            studyingSubject.setCurrentLesson(lesson);
//            studyingSubject.setCurrentTopic(topic);
//            studyingSubject.setSubject(subject);
//
//            userCurrentlyStudyingRepository.save(studyingSubject);
//        }
//
////        updateUserAverageChapterCompletion(user);
//        return responseUtil.successResponse(null);
//    }

    @Override
    public ResponseEntity<ResponseDTO<List<UserCurrentlyStudyingResponse>>> getAllUserCurrentlyStudying(HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);
        List<UserCurrentlyStudying> subjects = userCurrentlyStudyingRepository.findAllByUserId(user.getId());
        if(subjects.isEmpty()){
            return responseUtil.errorResponse("User Currently Studying Not Found");
        }
        List<UserCurrentlyStudyingResponse> userCurrentlyStudyingResponses = entityMapperUtil.convertToUserCurrentlyStudyingResponseList(subjects);

        if(userCurrentlyStudyingResponses.isEmpty()) throw new NotFoundException("User Progress Not Found");
        return responseUtil.successResponse(userCurrentlyStudyingResponses);
    }

    @Override
    public ResponseEntity<ResponseDTO<List<UserCurrentlyStudyingResponse>>> getUserCurrentlyStudying(Long subjectId, HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);

        List<UserCurrentlyStudying> userCurrentlyStudyingList = userCurrentlyStudyingRepository
                .findByUserIdAndSubjectId(user.getId(), subjectId);

        if (userCurrentlyStudyingList.isEmpty()) {
            throw new NotFoundException("User currently studying records not found for the specified subject");
        }

        List<UserCurrentlyStudyingResponse> userCurrentlyStudyingResponses = userCurrentlyStudyingList.stream()
                .map(entityMapperUtil::convertToUserCurrentlyStudyingResponse)
                .toList();

        return responseUtil.successResponse(userCurrentlyStudyingResponses);
    }

//    @Override
//    public ResponseEntity<ResponseDTO<List<UserCurrentlyStudyingResponse>>> getUserCurrentlyStudying(Long subjectId, HttpServletRequest request) {
//        User user = jwtUtils.getUserDataFromRequest(request);
//
//        List<UserCurrentlyStudying> userCurrentlyStudying = userCurrentlyStudyingRepository
//                .findByUserIdAndSubjectId(user.getId(), subjectId)
//                .orElseThrow(() -> new NotFoundException("User currently studying record not found for the specified subject"));
//
//        UserCurrentlyStudyingResponse userCurrentlyStudyingResponse = entityMapperUtil.convertToUserCurrentlyStudyingResponse(userCurrentlyStudying);
//
//        return responseUtil.successResponse(userCurrentlyStudyingResponse);
//    }

    @Override
    public ResponseEntity<ResponseDTO<List<UserCurrentlyStudyingResponse>>> searchBySubjectName(String subjectName, HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);

        List<UserCurrentlyStudying> studyingSubjects = userCurrentlyStudyingRepository
                .findAllByUserIdAndSubjectSubjectNameContainingIgnoreCase(user.getId(), subjectName);

        if (studyingSubjects.isEmpty()) {
            throw new NotFoundException("No records found for the subject name: " + subjectName);
        }

        List<UserCurrentlyStudyingResponse> responses = entityMapperUtil.convertToUserCurrentlyStudyingResponseList(studyingSubjects);

        return responseUtil.successResponse(responses);
    }

    private int calculateChapterCompletionPercentage(Long chapterId, Long userId) {
        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new NotFoundException("Chapter not found"));
        List<Lesson> lessons = chapter.getLessons();

        long totalTopics = lessons.stream()
                .mapToLong(lesson -> lesson.getTopics().size())
                .sum();

        if (totalTopics == 0) {
            return 0;
        }

        long completedTopicCount = lessons.stream()
                .flatMap(lesson -> lesson.getTopics().stream())
                .filter(topic -> topicCompletedRepository.countByTopicIdAndUserId(topic.getId(), userId) > 0)
                .count();

        int completionPercentage = (int) ((completedTopicCount * 100.0) / totalTopics);
        return completionPercentage;
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

    private int calculateSubjectCompletionPercentage(Long subjectId, Long userId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new NotFoundException("Subject not found"));

        List<Chapter> chapters = subject.getChapters();
        long totalChapters = chapters.size();

        if (totalChapters == 0) {
            return 0;
        }

        long completedChapterCount = chapters.stream()
                .mapToLong(chapter -> calculateChapterCompletionPercentage(chapter.getId(), userId))
                .sum();

        int completionPercentage = (int) ((completedChapterCount * 100.0) / (totalChapters * 100));
        return completionPercentage;
    }

//    private float calculateSubjectCompletionPercentage(Long subjectId, Long userId, Long topicId) {
//
//        if(topicRepository.existsById(topicId))
//        Topic topic = topicRepository.findById(topicId).orElseThrow(()-> new NotFoundException("Topic not found"));
//        Lesson lesson = topic.getLesson();
//        int totalTopicsInLesson = lesson.getTopics().size();
//        float completedTopicPerLesson = (float) 1/totalTopicsInLesson;
//        Chapter chapter = lesson.getChapter();
//        int totalLessonsInChapter = chapter.getLessons().size();
//        float completedTopicPerChapter = (float) completedTopicPerLesson/totalLessonsInChapter;
//        Subject subject = chapter.getSubject();
//        int totalChaptersPerSubject = subject.getChapters().size();
//        float completedTopicPerSubject = (float) completedTopicPerChapter/totalChaptersPerSubject;
//        return completedTopicPerSubject;
//
//
//
////        Subject subject = subjectRepository.findById(subjectId)
////                .orElseThrow(() -> new NotFoundException("Subject not found"));
////
////        List<Chapter> chapters = subject.getChapters();
////        long totalChapters = chapters.size();
////
////        if (totalChapters == 0) {
////            return 0;
////        }
////
////        long completedChapterCount = chapters.stream()
////                .mapToLong(chapter -> calculateChapterCompletionPercentage(chapter.getId(), userId))
////                .sum();
////
////        int completionPercentage = (int) ((completedChapterCount * 100.0) / (totalChapters * 100));
////        return completionPercentage;
//    }


}
