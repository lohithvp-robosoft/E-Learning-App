package com.robosoft.elearning.dto.response;

public class TopicWithTopicNameResponse {
    private Long id;
    private Long lessonId;
    private String heading;
    private String subHeading;

    public TopicWithTopicNameResponse(Long id, Long lessonId, String heading, String subHeading) {
        this.id = id;
        this.lessonId = lessonId;
        this.heading = heading;
        this.subHeading = subHeading;
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