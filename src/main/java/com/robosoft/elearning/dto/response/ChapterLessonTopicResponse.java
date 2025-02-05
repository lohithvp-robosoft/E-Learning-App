package com.robosoft.elearning.dto.response;

import java.util.List;

public class ChapterLessonTopicResponse {
    private Long id;
    private String chapterName;
    private List<LessonResponse> lessons;

    public ChapterLessonTopicResponse(Long id, String chapterName, List<LessonResponse> lessons) {
        this.id = id;
        this.chapterName = chapterName;
        this.lessons = lessons;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public List<LessonResponse> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonResponse> lessons) {
        this.lessons = lessons;
    }
}