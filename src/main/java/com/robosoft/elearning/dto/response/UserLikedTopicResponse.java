package com.robosoft.elearning.dto.response;

import com.robosoft.elearning.modal.Level;

public class UserLikedTopicResponse {
    private Long topicId;
    private String heading;
    private String subHeading;
    private Level level;
    private String icon;
    private String lessonName;
    private Integer chapterIndex;
    private Integer pageNumber;
    private Long chapterId;
    private Long lessonId;


    public UserLikedTopicResponse(Long id,String heading, String subHeading, Level level, String icon, String lessonName, Integer chapterIndex, Integer pageNumber, Long chapterId, Long lessonId) {
        this.topicId = id;
        this.heading = heading;
        this.subHeading = subHeading;
        this.level = level;
        this.icon = icon;
        this.chapterIndex = chapterIndex;
        this.lessonName = lessonName;
        this.pageNumber = pageNumber;
        this.chapterId = chapterId;
        this.lessonId = lessonId;
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

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
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

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }
}