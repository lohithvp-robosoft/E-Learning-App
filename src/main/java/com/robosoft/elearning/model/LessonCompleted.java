package com.robosoft.elearning.model;

import jakarta.persistence.*;

@Entity
public class LessonCompleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long lessonId;
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    public LessonCompleted(){}

    public LessonCompleted(Long lessonId, Long userId, Chapter chapter) {
        this.lessonId = lessonId;
        this.userId = userId;
        this.chapter = chapter;
    }

    public Long getId() {
        return id;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }
}
