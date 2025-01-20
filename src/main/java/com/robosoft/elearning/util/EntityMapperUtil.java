package com.robosoft.elearning.util;

import com.robosoft.elearning.dto.response.*;
import com.robosoft.elearning.modal.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityMapperUtil {

    public UserDetailResponse convertUserToUserDetailResponse(User user) {
        if (user == null) {
            return null;
        }

        UserDetailResponse userDetailResponse = new UserDetailResponse();

        userDetailResponse.setId(user.getId());
        userDetailResponse.setEmail(user.getEmail());
        userDetailResponse.setUserName(user.getUserName());
        userDetailResponse.setProfileImageUrl(user.getProfileImageUrl());

//        userDetailResponse.setCompleterCompletedInPercentage(0.0);
//        userDetailResponse.setAverageTestScore(0.0);
//        userDetailResponse.setHighestTestScore(0.0);
//        userDetailResponse.setNotificationEnabled(true);

        return userDetailResponse;
    }

    public SubjectResponse convertSubjectToSubjectResponse(Subject subject) {
        if (subject == null) {
            return null;
        }

        SubjectResponse subjectResponse = new SubjectResponse();
        subjectResponse.setId(subject.getId());
        subjectResponse.setSubjectName(subject.getSubjectName());
        subjectResponse.setSubjectIcon(subject.getSubjectIcon());

        return subjectResponse;
    }

    public ChapterResponse convertChapterToChapterResponse(Chapter chapter) {
        if (chapter == null) {
            return null;
        }

        ChapterResponse chapterResponse = new ChapterResponse();
        chapterResponse.setId(chapter.getId());
        chapterResponse.setChapterName(chapter.getChapterName());

        List<LessonResponse> lessonResponses = chapter.getLessons().stream()
                .map(this::convertLessonToLessonResponse)
                .collect(Collectors.toList());

        chapterResponse.setLessons(lessonResponses);

        return chapterResponse;
    }

    public LessonResponse convertLessonToLessonResponse(Lesson lesson) {
        if (lesson == null) {
            return null;
        }

        LessonResponse lessonResponse = new LessonResponse();
        lessonResponse.setId(lesson.getId());
        lessonResponse.setLessonName(lesson.getLessonName());
        lessonResponse.setLessonImg(Collections.singletonList(lesson.getLessonImg()));

        List<TopicResponse> topicResponses = lesson.getTopics().stream()
                .map(this::convertTopicToTopicResponse)
                .collect(Collectors.toList());

        lessonResponse.setTopics(topicResponses);

        return lessonResponse;
    }

    public TopicResponse convertTopicToTopicResponse(Topic topic) {
        if (topic == null) {
            return null;
        }

        TopicResponse topicResponse = new TopicResponse();
        topicResponse.setId(topic.getId());
        topicResponse.setHeading(topic.getHeading());
        topicResponse.setSubHeading(topic.getSubHeading());
        topicResponse.setContent(topic.getContents().toString());
        topicResponse.setLevel(topic.getLevel());

        return topicResponse;
    }


    public UpdateCompletedChapterResponse convertCompletedChapterToResponse(CompletedChapter completedChapter) {
        if (completedChapter == null) {
            return null;
        }

        UpdateCompletedChapterResponse response = new UpdateCompletedChapterResponse();
        response.setId(completedChapter.getId());
        response.setChapterId(completedChapter.getChapter().getId());
        response.setChapterName(completedChapter.getChapter().getChapterName());
        response.setCompleted(completedChapter.isCompleted());
        response.setCompletionDate(completedChapter.getCompletionDate());

        return response;
    }




}
