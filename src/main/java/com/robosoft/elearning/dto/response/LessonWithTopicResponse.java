package com.robosoft.elearning.dto.response;

import java.util.List;

public class LessonWithTopicResponse {
    private Long id;
    private Long chapterId;
    private Long lessonIndex;
    private String lessonName;
    private int completedLessonInPercentage;
    private List<TopicWithTopicNameResponse> topics;

    public LessonWithTopicResponse(Long id, Long chapterId, Long lessonIndex, String lessonName, int completedLessonInPercentage, List<TopicWithTopicNameResponse> topics) {
        this.id = id;
        this.chapterId = chapterId;
        this.lessonIndex = lessonIndex;
        this.lessonName = lessonName;
        this.completedLessonInPercentage = completedLessonInPercentage;
        this.topics = topics;
    }

    public int getCompletedLessonInPercentage() {
        return completedLessonInPercentage;
    }

    public void setCompletedLessonInPercentage(int completedLessonInPercentage) {
        this.completedLessonInPercentage = completedLessonInPercentage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public Long getLessonIndex() {
        return lessonIndex;
    }

    public void setLessonIndex(Long lessonIndex) {
        this.lessonIndex = lessonIndex;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public List<TopicWithTopicNameResponse> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicWithTopicNameResponse> topics) {
        this.topics = topics;
    }
}