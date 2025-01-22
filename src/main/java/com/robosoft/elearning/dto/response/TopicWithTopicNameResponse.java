package com.robosoft.elearning.dto.response;

public class TopicWithTopicNameResponse {
    private Long id;
    private Long subjectId;
    private String heading;
    private String subHeading;

    public TopicWithTopicNameResponse(Long id, Long subjectId, String heading, String subHeading) {
        this.id = id;
        this.subjectId = subjectId;
        this.heading = heading;
        this.subHeading = subHeading;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
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
