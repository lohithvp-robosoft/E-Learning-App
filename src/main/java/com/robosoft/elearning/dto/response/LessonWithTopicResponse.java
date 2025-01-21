package com.robosoft.elearning.dto.response;

import java.util.List;

public class LessonWithTopicResponse {
    private String lessonNumber;
    private String lessonName;
    private List<TopicWithTopicsResponse> topics;

    public LessonWithTopicResponse(String lessonNumber, String lessonName, List<TopicWithTopicsResponse> topics) {
        this.lessonNumber = lessonNumber;
        this.lessonName = lessonName;
        this.topics = topics;
    }

    public void setTopics(List<TopicWithTopicsResponse> topics) {
        this.topics = topics;
    }

    public List<TopicWithTopicsResponse> getTopics() {
        return topics;
    }

    public String getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(String lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }


}
