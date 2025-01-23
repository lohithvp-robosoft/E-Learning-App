package com.robosoft.elearning.dto.response;

import java.util.List;

public class ChapterLessonsResponse {
    private List<LessonWithTopicResponse> lessons;

    public ChapterLessonsResponse(List<LessonWithTopicResponse> lessons) {
        this.lessons = lessons;
    }

    public List<LessonWithTopicResponse> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonWithTopicResponse> lessons) {
        this.lessons = lessons;
    }
}