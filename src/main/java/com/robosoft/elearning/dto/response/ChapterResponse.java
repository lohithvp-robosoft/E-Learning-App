package com.robosoft.elearning.dto.response;

import java.util.List;

public class ChapterResponse {

    private Long id;
    private String chapterName;
    private String chapterImg;


    public ChapterResponse(Long id, String chapterName, String chapterImg) {
        this.id = id;
        this.chapterName = chapterName;
        this.chapterImg = chapterImg;
    }

    public ChapterResponse() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChapterImg() {
        return chapterImg;
    }

    public void setChapterImg(String chapterImg) {
        this.chapterImg = chapterImg;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
}
