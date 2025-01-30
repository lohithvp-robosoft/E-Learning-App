package com.robosoft.elearning.dto.response;

import com.robosoft.elearning.modal.Level;

public class TopicWithTopicsResponse {
    private Level level;
    private String heading;
    private String icon;
    private String subHeading;
    private Long topicId;
    private int pageStartsFrom;

    public TopicWithTopicsResponse(Level level, String heading, String icon, String subHeading, Long topicId, int pageStartsFrom) {
        this.level = level;
        this.heading = heading;
        this.icon = icon;
        this.subHeading = subHeading;
        this.topicId = topicId;
        this.pageStartsFrom = pageStartsFrom;
    }

    // Getters and Setters
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSubHeading() {
        return subHeading;
    }

    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public int getPageStartsFrom() {
        return pageStartsFrom;
    }

    public void setPageStartsFrom(int pageStartsFrom) {
        this.pageStartsFrom = pageStartsFrom;
    }
}
