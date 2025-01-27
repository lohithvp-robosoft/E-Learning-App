package com.robosoft.elearning.dto.request;

import com.robosoft.elearning.modal.Level;

public class TestRequest {
    private String heading;
    private Level level;
    private String testIcon;
    private Integer totalTime;
    private Long lessonId;

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getTestIcon() {
        return testIcon;
    }

    public void setTestIcon(String testIcon) {
        this.testIcon = testIcon;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }
}
