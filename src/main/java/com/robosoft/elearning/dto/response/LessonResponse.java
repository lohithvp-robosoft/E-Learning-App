package com.robosoft.elearning.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.robosoft.elearning.model.Level;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class LessonResponse {

    private Long id;
    private Long lessonIndex;
    private String lessonName;
    private Level level;
    private String heading;
    private String subheading;
    private String lessonImg;

    public LessonResponse(Long lessonIndex, String lessonName, String lessonImg, Level level, String heading, String subheading) {
        this.lessonIndex = lessonIndex;
        this.lessonName = lessonName;
        this.lessonImg = lessonImg;
        this.level = level;
        this.heading = heading;
        this.subheading = subheading;
    }

    public LessonResponse() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLessonIndex() {
        return lessonIndex;
    }

    public void setLessonIndex(Long lessonIndex) {
        this.lessonIndex = lessonIndex;
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

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getLessonImg() {
        return lessonImg;
    }

    public void setLessonImg(String lessonImg) {
        this.lessonImg = lessonImg;
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


}