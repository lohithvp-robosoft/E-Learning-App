package com.robosoft.elearning.dto.response;

public class ChapterRecommendationResponse {
    private Long chapterId;
    private String subjectName;
    private String chapterName;
    private String chapterImage;

    public ChapterRecommendationResponse(Long chapterId, String subjectName, String chapterName, String chapterImage) {
        this.chapterId = chapterId;
        this.subjectName = subjectName;
        this.chapterName = chapterName;
        this.chapterImage = chapterImage;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
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

    public String getChapterImage() {
        return chapterImage;
    }

    public void setChapterImage(String chapterImage) {
        this.chapterImage = chapterImage;
    }
}
