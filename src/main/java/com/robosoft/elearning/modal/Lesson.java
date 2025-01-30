package com.robosoft.elearning.modal;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;

@Entity
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lessonName;
    private String lessonImg;

    private Level level;
    private String heading;
    private String subheading;

    @ManyToOne
    @JsonBackReference
    private Chapter chapter;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    private List<Test> tests;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    private List<Topic> topics;

    public Lesson(Long lessonId) {
    }

    public Lesson() {
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

    public String getLessonImg() {
        return lessonImg;
    }

    public void setLessonImg(String lessonImg) {
        this.lessonImg = lessonImg;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
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

    public void calculatePageNumbers() {
        int currentPage = 1;

        for (Topic topic : topics) {
            topic.setPageStartsFrom(currentPage);
            // You can adjust this logic if you want to add specific page numbers for each topic
            // For example, if each topic takes 10 pages, you can increment by 10
            currentPage += 10; // Assuming each topic spans 10 pages, adjust as necessary
        }
    }
}

