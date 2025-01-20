package com.robosoft.elearning.dto.response;

import java.util.List;

public class LessonResponse {
    private Long id;
    private String lessonName;
    private List<String> lessonImg;
    private List<TopicResponse> topics;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public List<String> getLessonImg() {
        return lessonImg;
    }

    public void setLessonImg(List<String> lessonImg) {
        this.lessonImg = lessonImg;
    }

    public List<TopicResponse> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicResponse> topics) {
        this.topics = topics;
    }
}
