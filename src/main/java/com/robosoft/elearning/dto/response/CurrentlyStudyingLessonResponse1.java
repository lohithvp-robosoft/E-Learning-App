package com.robosoft.elearning.dto.response;

import java.util.List;

public class CurrentlyStudyingLessonResponse1 {
    private Long lessonId;
    private String lessonName;
    private Long lessonIndex;
    private Long chapterId;
    private boolean currentlyStudyingLesson;
    private int completedLessonInPercentage;
    private List<TopicWithTopicNameResponse> topics;

    public CurrentlyStudyingLessonResponse1(Long lessonId, String lessonName, Long lessonIndex, Long chapterId,
                                           boolean currentlyStudyingLesson, int completedLessonInPercentage,
                                           List<TopicWithTopicNameResponse> topics) {
        this.lessonId = lessonId;
        this.lessonName = lessonName;
        this.lessonIndex = lessonIndex;
        this.chapterId = chapterId;
        this.currentlyStudyingLesson = currentlyStudyingLesson;
        this.completedLessonInPercentage = completedLessonInPercentage;
        this.topics = topics;
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

    public Long getLessonIndex() {
        return lessonIndex;
    }

    public void setLessonIndex(Long lessonIndex) {
        this.lessonIndex = lessonIndex;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public boolean isCurrentlyStudyingLesson() {
        return currentlyStudyingLesson;
    }

    public void setCurrentlyStudyingLesson(boolean currentlyStudyingLesson) {
        this.currentlyStudyingLesson = currentlyStudyingLesson;
    }

    public int getCompletedLessonInPercentage() {
        return completedLessonInPercentage;
    }

    public void setCompletedLessonInPercentage(int completedLessonInPercentage) {
        this.completedLessonInPercentage = completedLessonInPercentage;
    }

    public List<TopicWithTopicNameResponse> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicWithTopicNameResponse> topics) {
        this.topics = topics;
    }

}
