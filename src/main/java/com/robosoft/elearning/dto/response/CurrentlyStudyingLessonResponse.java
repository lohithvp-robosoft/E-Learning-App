package com.robosoft.elearning.dto.response;

import java.util.List;

    public class CurrentlyStudyingLessonResponse {

    private Long lessonId;
    private String lessonName;
    private Integer lessonIndex;
    private Long chapterId;
    private Boolean currentlyStudyingLesson;
    private Integer completedLessonInPercentage;
    private List<TopicWithTopicNameResponse> topics;

    public CurrentlyStudyingLessonResponse(Long lessonId, String lessonName, Integer lessonIndex, Long chapterId,
                                           Boolean currentlyStudyingLesson, Integer completedLessonInPercentage,
                                           List<TopicWithTopicNameResponse> topics) {
        this.lessonId = lessonId;
        this.lessonName = lessonName;
        this.lessonIndex = lessonIndex;
        this.chapterId = chapterId;
        this.currentlyStudyingLesson = currentlyStudyingLesson;
        this.completedLessonInPercentage = completedLessonInPercentage;
        this.topics = topics;
    }

    public CurrentlyStudyingLessonResponse(Long id, String subjectName, Long aLong, List<ChapterSummaryResponse> chapters) {
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public Integer getLessonIndex() {
        return lessonIndex;
    }

    public void setLessonIndex(Integer lessonIndex) {
        this.lessonIndex = lessonIndex;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public Boolean getCurrentlyStudyingLesson() {
        return currentlyStudyingLesson;
    }

    public void setCurrentlyStudyingLesson(Boolean currentlyStudyingLesson) {
        this.currentlyStudyingLesson = currentlyStudyingLesson;
    }

    public Integer getCompletedLessonInPercentage() {
        return completedLessonInPercentage;
    }

    public void setCompletedLessonInPercentage(Integer completedLessonInPercentage) {
        this.completedLessonInPercentage = completedLessonInPercentage;
    }

    public List<TopicWithTopicNameResponse> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicWithTopicNameResponse> topics) {
        this.topics = topics;
    }
}