package com.robosoft.elearning.modal;

import jakarta.persistence.*;

@Entity
public class UserCurrentlyStudying {
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

    @ManyToOne
    private Topic currentTopic;

    private Float completedChapterInPercentage;

    private Float completedLessonInPercentage;


    public UserCurrentlyStudying(User user) {
        this.user = user;
        this.completedChapterInPercentage = 0.f;
        this.completedLessonInPercentage = 0f;
    }

    public UserCurrentlyStudying(){}

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

    public Float getCompletedChapterInPercentage() {
        return completedChapterInPercentage;
    }

    public void setCompletedChapterInPercentage(Float completedChapterInPercentage) {
        this.completedChapterInPercentage = completedChapterInPercentage;
    }

    public Topic getCurrentTopic() {
        return currentTopic;
    }

    public void setCurrentTopic(Topic currentTopic) {
        this.currentTopic = currentTopic;
    }


    public Float getCompletedLessonInPercentage() {
        return completedLessonInPercentage;
    }

    public void setCompletedLessonInPercentage(Float completedLessonInPercentage) {
        this.completedLessonInPercentage = completedLessonInPercentage;
    }
}
