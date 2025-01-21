package com.robosoft.elearning.dto.response;

import java.util.List;

public class ChapterResponse {

    private Long id;
    private String chapterName;
    private List<LessonResponse> lessons;
    private String chapterImg;

//    public ChapterResponse(String chapterName, List<LessonResponse> lessonResponses) {
//        this.chapterName = chapterName;
//        this.lessons = lessonResponses;
//    }

    public ChapterResponse(Long id, String chapterName, String chapterImg) {
        this.id = id;
        this.chapterName = chapterName;
        this.chapterImg = chapterImg;
    }


    public ChapterResponse() {

    }

    public ChapterResponse(String chapterName, List<LessonResponse> lessonResponses) {
        this.chapterName = chapterName;
        this.lessons = lessonResponses;
    }

    public ChapterResponse(Long id, String chapterName, String chapterImg, List<LessonResponse> lessonResponses) {
        this.id = id;
        this.chapterName = chapterName;
        this.lessons = lessonResponses;
        this.chapterImg = chapterImg;
    }

    public String getChapterImg() {
        return chapterImg;
    }

    public void setChapterImg(String chapterImg) {
        this.chapterImg = chapterImg;
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

