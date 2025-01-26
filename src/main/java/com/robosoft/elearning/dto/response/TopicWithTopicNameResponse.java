package com.robosoft.elearning.dto.response;

public class TopicWithTopicNameResponse {
    private Long topicId;
    private Long lessonId;
    private String heading;
    private String subHeading;
    private boolean isCompleted;
    private Long subjectId;

    public TopicWithTopicNameResponse(Long topicId, Long lessonId, String heading, String subHeading, boolean isCompleted, Long subjectId) {
        this.topicId = topicId;
        this.lessonId = lessonId;
        this.heading = heading;
        this.subHeading = subHeading;
        this.isCompleted = isCompleted;
        this.subjectId = subjectId;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getSubHeading() {
        return subHeading;
    }

    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }
}