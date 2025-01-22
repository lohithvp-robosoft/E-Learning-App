package com.robosoft.elearning.dto.response;

import java.util.List;

public class LessonWithTopicResponse {
    private Long lessonIndex;
    private String lessonName;
    private List<TopicWithTopicNameResponse> topics;

    public LessonWithTopicResponse(Long lessonIndex, String lessonName, List<TopicWithTopicNameResponse> topics) {
        this.lessonIndex = lessonIndex;
        this.lessonName = lessonName;
        this.topics = topics;
    }

    public void setTopics(List<TopicWithTopicNameResponse> topics) {
        this.topics = topics;
    }

    public List<TopicWithTopicNameResponse> getTopics() {
        return topics;
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


}
