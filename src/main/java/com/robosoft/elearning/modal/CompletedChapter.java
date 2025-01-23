package com.robosoft.elearning.modal;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class CompletedChapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserCurrentlyStudying userCurrentlyStudying;

    @ManyToOne
    private Chapter chapter;

    @OneToMany(mappedBy = "completedChapter", cascade = CascadeType.ALL)
    private List<CompletedLesson> completedLessons;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserCurrentlyStudying getUserCurrentlyStudyingSubject() {
        return userCurrentlyStudying;
    }

    public void setUserCurrentlyStudyingSubject(UserCurrentlyStudying userCurrentlyStudying) {
        this.userCurrentlyStudying = userCurrentlyStudying;
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

