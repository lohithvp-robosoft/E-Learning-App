package com.robosoft.elearning.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class UserTestScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserTestResult userTestResult;

    @ManyToOne
    private Test test;

    @ManyToOne
    private User user;

    private Integer totalAnsweredQuestions;
    private Integer totalMarks;

    private Integer totalCorrectAnswers;

    private Integer totalNumberOfQuestion;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserTestResult getUserTestResult() {
        return userTestResult;
    }

    public void setUserTestResult(UserTestResult userTestResult) {
        this.userTestResult = userTestResult;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Integer getTotalAnsweredQuestions() {
        return totalAnsweredQuestions;
    }

    public void setTotalAnsweredQuestions(Integer totalAnsweredQuestions) {
        this.totalAnsweredQuestions = totalAnsweredQuestions;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }

    public Integer getTotalCorrectAnswers() {
        return totalCorrectAnswers;
    }

    public void setTotalCorrectAnswers(Integer totalCorrectAnswers) {
        this.totalCorrectAnswers = totalCorrectAnswers;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Integer getTotalNumberOfQuestion(int totalQuestions) {
        return totalNumberOfQuestion;
    }

    public void setTotalNumberOfQuestion(Integer totalNumberOfQuestion) {
        this.totalNumberOfQuestion = totalNumberOfQuestion;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getTotalNumberOfQuestion() {
        return totalNumberOfQuestion;
    }
}
