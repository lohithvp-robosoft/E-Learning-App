package com.robosoft.elearning.dto.response;

public class UserCurrentlyStudyingResponse {
    private Long id;
    private Long subjectId;
    private String subjectName;
    private String chapterImage;
    private Float completedChapterInPercentage;
    private Float currentlyStudingLessonInPercentage;
    private long chapterId;
    private long lessonId;
    private String chapterName;
    private String lessonName;
    private String topicName;

    public UserCurrentlyStudyingResponse(Long id, Long subjectId, String subjectName, Float completedChapterInPercentage, String chapterName, long chapterId, long lessonId, String lessonName, String topicName, String chapterImage, Float currentlyStudingLessonInPercentage) {
        this.id = id;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.completedChapterInPercentage = completedChapterInPercentage;
        this.chapterId = chapterId;
        this.lessonId = lessonId;
        this.chapterName = chapterName;
        this.lessonName = lessonName;
        this.topicName = topicName;
        this.chapterImage = chapterImage;
        this.currentlyStudingLessonInPercentage = currentlyStudingLessonInPercentage;
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

    public Float getCompletedChapterInPercentage() {
        return completedChapterInPercentage;
    }

    public void setCompletedChapterInPercentage(Float completedChapterInPercentage) {
        this.completedChapterInPercentage = completedChapterInPercentage;
    }

    public String getChapterImage() {
        return chapterImage;
    }

    public void setChapterImage(String chapterImage) {
        this.chapterImage = chapterImage;
    }

    public long getChapterId() {
        return chapterId;
    }

    public void setChapterId(long chapterId) {
        this.chapterId = chapterId;
    }

    public long getLessonId() {
        return lessonId;
    }

    public void setLessonId(long lessonId) {
        this.lessonId = lessonId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Float getCurrentlyStudingLessonInPercentage() {
        return currentlyStudingLessonInPercentage;
    }

    public void setCurrentlyStudingLessonInPercentage(Float currentlyStudingLessonInPercentage) {
        this.currentlyStudingLessonInPercentage = currentlyStudingLessonInPercentage;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }
}
