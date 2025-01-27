package com.robosoft.elearning.dto.response;

import com.robosoft.elearning.modal.Level;
import com.robosoft.elearning.modal.Question;

import java.time.LocalDateTime;
import java.util.List;

public class TestResponse {
    private Long id;
    private String testIcon;
    private String heading;
    private Level level;
    private Integer totalTime;
    private String lessonName;
    private String subHeading;
    private Integer totalNumberOfQuestions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long lessonId;

    public TestResponse() {
    }

    public TestResponse(Long id, String testIcon,String heading, Level level, Integer totalTime, String lessonName,Integer totalNumberOfQuestions, String subHeading,LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.testIcon = testIcon;
        this.heading = heading;
        this.level = level;
        this.totalTime = totalTime;
        this.lessonName = lessonName;
        this.subHeading = subHeading;
        this.totalNumberOfQuestions = totalNumberOfQuestions;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSubHeading() {
        return subHeading;
    }

    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }


    public String getTestIcon() {
        return testIcon;
    }

    public void setTestIcon(String testIcon) {
        this.testIcon = testIcon;
    }

    public Integer getTotalNumberOfQuestions() {
        return totalNumberOfQuestions;
    }

    public void setTotalNumberOfQuestions(Integer totalNumberOfQuestions) {
        this.totalNumberOfQuestions = totalNumberOfQuestions;
    }

    @Override
    public String toString() {
        return "TestResponse{" +
                "id=" + id +
                ", testIcom" + testIcon + '\'' +
                ", heading='" + heading + '\'' +
                ", level=" + level +
                ", totalTime=" + totalTime +
                ", lessonName='" + lessonName + '\''+
                ", subHeading" + subHeading+
                ", Total number of questions" + totalNumberOfQuestions+
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
