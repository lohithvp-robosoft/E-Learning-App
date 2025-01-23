package com.robosoft.elearning.dto.response;

import com.robosoft.elearning.modal.Level;

public class UserLikedTopicResponse {
    private Long id;
    private String heading;
    private String subHeading;
    private Level level;
    private String icon;
    private String lessonName;
    private Integer chapterIndex;


    public UserLikedTopicResponse(Long id,String heading, String subHeading, Level level, String icon, String lessonName, Integer chapterIndex) {
        this.id = id;
        this.heading = heading;
        this.subHeading = subHeading;
        this.level = level;
        this.icon = icon;
        this.chapterIndex = chapterIndex;
        this.lessonName = lessonName;
    }

    public UserLikedTopicResponse() {

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


    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public Integer getChapterIndex() {
        return chapterIndex;
    }

    public void setChapterIndex(Integer chapterIndex) {
        this.chapterIndex = chapterIndex;
    }
}