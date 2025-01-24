package com.robosoft.elearning.dto.response;

public class TopicWithTopicNameResponse {
    private Long id;
    private Long lessonId;
    private String heading;
    private String subHeading;
    private boolean isCompleted;

    public TopicWithTopicNameResponse(boolean isCompleted, String subHeading, String heading, Long lessonId, Long id) {
        this.isCompleted = isCompleted;
        this.subHeading = subHeading;
        this.heading = heading;
        this.lessonId = lessonId;
        this.id = id;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}