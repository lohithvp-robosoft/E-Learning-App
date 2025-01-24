package com.robosoft.elearning.dto.response;

public class ChapterSummaryResponse {

    private Long Id;
    private String chapterName;
    private String chapterImg;
    private boolean isCurrentlyStudying;

    public ChapterSummaryResponse() {
    }


    public ChapterSummaryResponse(Long id, String chapterName, String chapterImg, boolean isCurrentlyStudying) {
        Id = id;
        this.chapterName = chapterName;
        this.chapterImg = chapterImg;
        this.isCurrentlyStudying = isCurrentlyStudying;
    }

    public boolean isCurrentlyStudying() {
        return isCurrentlyStudying;
    }

    public void setCurrentlyStudying(boolean currentlyStudying) {
        isCurrentlyStudying = currentlyStudying;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterImg() {
        return chapterImg;
    }

    public void setChapterImg(String chapterImg) {
        this.chapterImg = chapterImg;
    }


}