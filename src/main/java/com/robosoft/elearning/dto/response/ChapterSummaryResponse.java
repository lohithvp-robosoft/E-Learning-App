package com.robosoft.elearning.dto.response;

public class ChapterSummaryResponse {

    private Long id;
    private String chapterName;
    private String chapterImg;
    private boolean isCurrentlyStudying;
    private Long subjectId;
    private String subjectName;

    public ChapterSummaryResponse() {}

    public ChapterSummaryResponse(Long id, String chapterName, String chapterImg, boolean isCurrentlyStudying, Long subjectId, String subjectName) {
        this.id = id;
        this.chapterName = chapterName;
        this.chapterImg = chapterImg;
        this.isCurrentlyStudying = isCurrentlyStudying;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    // Getters and Setters
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

    public String getChapterImg() {
        return chapterImg;
    }

    public void setChapterImg(String chapterImg) {
        this.chapterImg = chapterImg;
    }

    public boolean isCurrentlyStudying() {
        return isCurrentlyStudying;
    }

    public void setCurrentlyStudying(boolean currentlyStudying) {
        isCurrentlyStudying = currentlyStudying;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}