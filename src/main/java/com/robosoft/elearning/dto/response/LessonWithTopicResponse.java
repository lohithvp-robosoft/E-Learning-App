package com.robosoft.elearning.dto.response;

import java.util.List;

public class LessonWithTopicResponse {
    private Long chapterId;
    private Long lessonIndex;
    private String lessonName;
    private List<TopicWithTopicNameResponse> topics;

    public LessonWithTopicResponse(Long chapterId, Long lessonIndex, String lessonName, List<TopicWithTopicNameResponse> topics) {
        this.chapterId = chapterId;
        this.lessonIndex = lessonIndex;
        this.lessonName = lessonName;
        this.topics = topics;
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