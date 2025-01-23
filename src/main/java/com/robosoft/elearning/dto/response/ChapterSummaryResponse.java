package com.robosoft.elearning.dto.response;

public class ChapterSummaryResponse {

    private Long subjectId;
    private String subjectName;
    private String chapterName;
    private String chapterImg;

    public ChapterSummaryResponse() {
    }

    public ChapterSummaryResponse(Long subjectId, String subjectName, String chapterName, String chapterImg) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.chapterName = chapterName;
        this.chapterImg = chapterImg;
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
