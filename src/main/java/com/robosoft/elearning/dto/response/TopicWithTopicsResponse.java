package com.robosoft.elearning.dto.response;

import com.robosoft.elearning.modal.Level;

public class TopicWithTopicsResponse {
    private Level level;
    private String heading;
    private String subheading;
    private String icon;

    public TopicWithTopicsResponse(Level level, String heading, String icon, String subheading) {
        this.level = level;
        this.heading = heading;
        this.icon = icon;
        this.subheading = subheading;
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
