package com.robosoft.elearning.dto.response;

import com.robosoft.elearning.modal.Level;

public class TopicWithTopicsResponse {
    private Level level;
    private String heading;
    private String subheading;
    private String icon;
    private Long topicId;


    public TopicWithTopicsResponse(Level level, String heading, String icon, String subheading, Long topicId) {
        this.level = level;
        this.heading = heading;
        this.icon = icon;
        this.subheading = subheading;
        this.topicId=topicId;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getSubheading() {
        return subheading;
    }

    public void setSubheading(String subheading) {
        this.subheading = subheading;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}