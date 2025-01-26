package com.robosoft.elearning.dto.response;

import java.util.List;

public class ChapterLessonsResponse {
    private Long chapterId;
    private String chapterName;
    private Long lessonId;
    private String lessonName;
    private Long lessonIndex;
    private List<TopicWithTopicsResponse>  topics;

    public ChapterLessonsResponse(Long chapterId, String chapterName, Long lessonId, String lessonName, Long lessonIndex, List<TopicWithTopicsResponse> topics) {
        this.chapterId = chapterId;
        this.chapterName = chapterName;
        this.lessonId = lessonId;
        this.lessonName = lessonName;
        this.lessonIndex = lessonIndex;
        this.topics = topics;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public Long getLessonIndex() {
        return lessonIndex;
    }

    public void setLessonIndex(Long lessonIndex) {
        this.lessonIndex = lessonIndex;
    }

    public List<TopicWithTopicsResponse> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicWithTopicsResponse> topics) {
        this.topics = topics;
    }
}