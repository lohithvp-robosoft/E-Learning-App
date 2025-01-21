package com.robosoft.elearning.dto.response;

import com.robosoft.elearning.modal.Level;

import java.util.List;

public class LessonResponse {

    private Long id;

    private String lessonNumber;
    private String lessonName;
    private Level level;
    private String heading;
    private String subheading;

    private String lessonImg;

    public LessonResponse(String lessonNumber, String lessonName, String lessonImg, Level level, String heading, String subheading) {
        this.lessonNumber = lessonNumber;
        this.lessonName = lessonName;
        this.lessonImg = lessonImg;
        this.level = level;
        this.heading = heading;
        this.subheading = subheading;
    }

    public LessonResponse() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(String lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public Level getLevel() {
        return level;
    }

    public String getLessonImg() {
        return lessonImg;
    }

    public void setLessonImg(String lessonImg) {
        this.lessonImg = lessonImg;
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
//    private Long id;
//    private String lessonName;
//    private List<String> lessonImg;
//    private List<TopicResponse> topics;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getLessonName() {
//        return lessonName;
//    }
//
//    public void setLessonName(String lessonName) {
//        this.lessonName = lessonName;
//    }
//
//    public List<String> getLessonImg() {
//        return lessonImg;
//    }
//
//    public void setLessonImg(List<String> lessonImg) {
//        this.lessonImg = lessonImg;
//    }
//
//    public List<TopicResponse> getTopics() {
//        return topics;
//    }
//
//    public void setTopics(List<TopicResponse> topics) {
//        this.topics = topics;
//    }
}
