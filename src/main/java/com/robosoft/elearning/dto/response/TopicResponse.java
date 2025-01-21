package com.robosoft.elearning.dto.response;

import com.robosoft.elearning.modal.Level;

public class TopicResponse {
    private Long id;
    private String heading;
    private String subHeading;
    private Level level;
    private String icon;
    private String content;

    public TopicResponse(String heading, String subHeading, Level level, String icon) {
        this.heading = heading;
        this.subHeading = subHeading;
        this.level = level;
        this.icon = icon;
    }

    public TopicResponse() {

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
