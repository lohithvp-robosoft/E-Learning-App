package com.robosoft.elearning.modal;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class CompletedChapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserCurrentlyStudyingSubject userCurrentlyStudyingSubject;

    @ManyToOne
    private Chapter chapter;

    @OneToMany(mappedBy = "completedChapter", cascade = CascadeType.ALL)
    private List<CompletedLesson> completedLessons;


    private boolean isCompleted;

    private String completionDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public boolean isCompleted() {
        return isCompleted;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserCurrentlyStudyingSubject getUserCurrentlyStudyingSubject() {
        return userCurrentlyStudyingSubject;
    }

    public void setUserCurrentlyStudyingSubject(UserCurrentlyStudyingSubject userCurrentlyStudyingSubject) {
        this.userCurrentlyStudyingSubject = userCurrentlyStudyingSubject;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public List<CompletedLesson> getCompletedLessons() {
        return completedLessons;
    }

    public void setCompletedLessons(List<CompletedLesson> completedLessons) {
        this.completedLessons = completedLessons;
    }
}

