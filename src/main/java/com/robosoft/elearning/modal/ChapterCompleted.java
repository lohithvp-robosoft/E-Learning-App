package com.robosoft.elearning.modal;

import jakarta.persistence.*;

@Entity
public class ChapterCompleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long chapterId;
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    public ChapterCompleted(Long chapterId, Long userId, Subject subject) {
        this.chapterId = chapterId;
        this.userId = userId;
        this.subject = subject;

    }

    public ChapterCompleted(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}