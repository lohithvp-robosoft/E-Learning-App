package com.robosoft.elearning.dto.response;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChapterNameResponse {
    private Long id;
    private String chapterName;
    private List<LessonWithTopicResponse> lessons;

    public ChapterNameResponse(String chapterName, List<LessonWithTopicResponse> lessons) {
        this.chapterName = chapterName;
        this.lessons = lessons;
    }

    public ChapterNameResponse() {

    }

    public ChapterNameResponse(Long id, String chapterName, List<LessonResponse> lessonResponses) {
        this.id=id;
        this.chapterName=chapterName;

    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public List<LessonWithTopicResponse> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonWithTopicResponse> lessons) {
        this.lessons = lessons;
    }
}