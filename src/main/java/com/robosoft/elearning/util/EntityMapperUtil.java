package com.robosoft.elearning.util;

import com.robosoft.elearning.dto.response.*;
import com.robosoft.elearning.modal.*;
import com.robosoft.elearning.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EntityMapperUtil {

    @Autowired
    private LessonRepository lessonRepository;

    public UserDetailResponse convertUserToUserDetailResponse(User user) {
        if (user == null) {
            return null;
        }

        UserDetailResponse userDetailResponse = new UserDetailResponse();

        userDetailResponse.setId(user.getId());
        userDetailResponse.setEmail(user.getEmail());
        userDetailResponse.setUserName(user.getUserName());
        userDetailResponse.setProfileImageUrl(user.getProfileImageUrl());

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

    public ChapterResponse convertChapterToChapterResponseWithoutLessons(Chapter chapter) {
        if (chapter == null) {
            return null;
        }
        ChapterResponse chapterResponse = new ChapterResponse();
        chapterResponse.setId(chapter.getId());
        chapterResponse.setChapterName(chapter.getChapterName());
        chapterResponse.setChapterImg(chapter.getChapterImg());

        return chapterResponse;
    }

    public ChapterResponse convertChapterToChapterResponse(Chapter chapter) {
        if (chapter == null) {
            return null;
        }

        ChapterResponse chapterResponse = new ChapterResponse();
        chapterResponse.setId(chapter.getId());
        chapterResponse.setChapterName(chapter.getChapterName());
        chapterResponse.setChapterImg(chapter.getChapterImg());

        List<LessonResponse> lessonResponses = new ArrayList<>();
        if (chapter.getLessons() != null) {
            for (Lesson lesson : chapter.getLessons()) {
                LessonResponse lessonResponse = new LessonResponse();
                Long lessonIndex = (long) lessonRepository.countByChapterIdAndIdLessThan(chapter.getId(), lesson.getId()) + 1;

                lessonResponse.setLessonIndex(lessonIndex);
                lessonResponse.setLessonName(lesson.getLessonName());
                lessonResponse.setLessonImg(lesson.getLessonImg());
                lessonResponse.setLevel(lesson.getLevel());
                lessonResponse.setHeading(lesson.getHeading());
                lessonResponse.setSubheading(lesson.getSubheading());
                lessonResponses.add(lessonResponse);
            }
        }
        return chapterResponse;
    }


    public LessonResponse convertLessonToLessonResponse(Lesson lesson) {
        if (lesson == null) {
            return null;
        }

        LessonResponse lessonResponse = new LessonResponse();
        lessonResponse.setId(lesson.getId());
        lessonResponse.setLessonName(lesson.getLessonName());
        lessonResponse.setLessonImg((lesson.getLessonImg()));

        return lessonResponse;
    }

    public TopicResponse convertTopicToTopicResponse(Topic topic) {
        if (topic == null) {
            return null;
        }

        TopicResponse topicResponse = new TopicResponse();
        topicResponse.setSubjectId(topic.getLesson().getChapter().getSubject().getId());
        topicResponse.setId(topic.getId());
        topicResponse.setHeading(topic.getHeading());
        topicResponse.setSubHeading(topic.getSubHeading());
        List<ContentResponse> contentResponseList = new ArrayList<>();
        topicResponse.setContent(contentResponseList);
        topicResponse.setLevel(topic.getLevel());

        return topicResponse;
    }


    public List<ContentResponse> convertContentListToResponseList(List<Content> contentList) {
        if (contentList == null || contentList.isEmpty()) {
            return new ArrayList<>();
        }

        List<ContentResponse> contentResponseList = new ArrayList<>();
        for (Content content : contentList) {
            ContentResponse contentResponse = new ContentResponse();
            contentResponse.setId(content.getId());
            contentResponse.setHeading(content.getHeading());
            contentResponseList.add(contentResponse);
        }

        return contentResponseList;
    }



}
