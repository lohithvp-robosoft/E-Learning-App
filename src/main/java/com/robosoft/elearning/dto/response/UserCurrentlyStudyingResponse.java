package com.robosoft.elearning.dto.response;

public class UserCurrentlyStudyingResponse {
    private Long id;
    private String subjectName;
    private String chapterImageUrl;
    private Integer completedChapterInPercentage;
    private String currentChapterTitle;
    private String currentLessonTitle;
    private String currentTopicTitle;

    public UserCurrentlyStudyingResponse(Long id, String subjectName, Integer completedChapterInPercentage, String currentChapterTitle, String currentLessonTitle, String currentTopicTitle, String chapterImageUrl) {
        this.id = id;
        this.subjectName = subjectName;
        this.completedChapterInPercentage = completedChapterInPercentage;
        this.currentChapterTitle = currentChapterTitle;
        this.currentLessonTitle = currentLessonTitle;
        this.currentTopicTitle = currentTopicTitle;
        this.chapterImageUrl = chapterImageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getCompletedChapterInPercentage() {
        return completedChapterInPercentage;
    }

    public void setCompletedChapterInPercentage(Integer completedChapterInPercentage) {
        this.completedChapterInPercentage = completedChapterInPercentage;
    }

    public String getCurrentChapterTitle() {
        return currentChapterTitle;
    }

    public void setCurrentChapterTitle(String currentChapterTitle) {
        this.currentChapterTitle = currentChapterTitle;
    }

    public String getCurrentLessonTitle() {
        return currentLessonTitle;
    }

    public void setCurrentLessonTitle(String currentLessonTitle) {
        this.currentLessonTitle = currentLessonTitle;
    }

    public String getCurrentTopicTitle() {
        return currentTopicTitle;
    }

    public void setCurrentTopicTitle(String currentTopicTitle) {
        this.currentTopicTitle = currentTopicTitle;
    }

    public String getChapterImageUrl() {
        return chapterImageUrl;
    }

    public void setChapterImageUrl(String chapterImageUrl) {
        this.chapterImageUrl = chapterImageUrl;
    }
}
