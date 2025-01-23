package com.robosoft.elearning.modal;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class UserTestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Lesson lesson;

    @OneToMany(mappedBy = "userTestResult", cascade = CascadeType.ALL)
    private List<UserTestScore> userTestScores;

    private Double averageScore;
    private Double highestScore;

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

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public List<UserTestScore> getUserTestScores() {
        return userTestScores;
    }

    public void setUserTestScores(List<UserTestScore> userTestScores) {
        this.userTestScores = userTestScores;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    public Double getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(Double highestScore) {
        this.highestScore = highestScore;
    }
}

