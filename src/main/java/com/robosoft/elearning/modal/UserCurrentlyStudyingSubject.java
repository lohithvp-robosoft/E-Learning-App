package com.robosoft.elearning.modal;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class UserCurrentlyStudyingSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private Chapter currentChapter;

    @ManyToOne
    private Lesson currentLesson;

    @OneToMany(mappedBy = "userCurrentlyStudyingSubject", cascade = CascadeType.ALL)
    private List<CompletedChapter> completedChapters;

    private Double completedChapterInPercentage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Chapter getCurrentChapter() {
        return currentChapter;
    }

    public void setCurrentChapter(Chapter currentChapter) {
        this.currentChapter = currentChapter;
    }

    public Lesson getCurrentLesson() {
        return currentLesson;
    }

    public void setCurrentLesson(Lesson currentLesson) {
        this.currentLesson = currentLesson;
    }

    public List<CompletedChapter> getCompletedChapters() {
        return completedChapters;
    }

    public void setCompletedChapters(List<CompletedChapter> completedChapters) {
        this.completedChapters = completedChapters;
    }

    public Double getCompletedChapterInPercentage() {
        return completedChapterInPercentage;
    }

    public void setCompletedChapterInPercentage(Double completedChapterInPercentage) {
        this.completedChapterInPercentage = completedChapterInPercentage;
    }
}
