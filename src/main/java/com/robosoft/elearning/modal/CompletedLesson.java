package com.robosoft.elearning.modal;

import jakarta.persistence.*;

@Entity
public class CompletedLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CompletedChapter completedChapter;

    @ManyToOne
    private Lesson lesson;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompletedChapter getCompletedChapter() {
        return completedChapter;
    }

    public void setCompletedChapter(CompletedChapter completedChapter) {
        this.completedChapter = completedChapter;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }
}

