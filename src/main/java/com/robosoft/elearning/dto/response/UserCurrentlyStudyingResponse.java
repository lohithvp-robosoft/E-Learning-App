package com.robosoft.elearning.dto.response;

public class UserCurrentlyStudyingResponse {
    private Long id;
    private String subjectName;
    private String chapterImageUrl;
    private Integer completedChapterInPercentage;
    private Integer completedLessonInPercentage;
    private long currentChapterId;
    private long currentLessonId;
    private String currentChapterTitle;
    private String currentLessonTitle;
    private String currentTopicTitle;

    public UserCurrentlyStudyingResponse(Long id, String subjectName, Integer completedChapterInPercentage, String currentChapterTitle, long currentChapterId, long currentLessonId, String currentLessonTitle, String currentTopicTitle, String chapterImageUrl, Integer completedLessonInPercentage) {
        this.id = id;
        this.subjectName = subjectName;
        this.completedChapterInPercentage = completedChapterInPercentage;
        this.currentChapterId = currentChapterId;
        this.currentLessonId = currentLessonId;
        this.currentChapterTitle = currentChapterTitle;
        this.currentLessonTitle = currentLessonTitle;
        this.currentTopicTitle = currentTopicTitle;
        this.chapterImageUrl = chapterImageUrl;
        this.completedLessonInPercentage = completedLessonInPercentage;
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

    public Integer getCompletedLessonInPercentage() {
        return completedLessonInPercentage;
    }

    public void setCompletedLessonInPercentage(Integer completedLessonInPercentage) {
        this.completedLessonInPercentage = completedLessonInPercentage;
    }

    public long getCurrentChapterId() {
        return currentChapterId;
    }

    public void setCurrentChapterId(long currentChapterId) {
        this.currentChapterId = currentChapterId;
    }

    public long getCurrentLessonId() {
        return currentLessonId;
    }

    public void setCurrentLessonId(long currentLessonId) {
        this.currentLessonId = currentLessonId;
    }
}
