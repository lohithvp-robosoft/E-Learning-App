package com.robosoft.elearning.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Entity
public class UserTestProgress {

    public enum TestStatus {
        IN_PROGRESS,
        COMPLETED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Test test;

    @ElementCollection
    @CollectionTable(name = "user_answers", joinColumns = @JoinColumn(name = "progress_id"))
    @MapKeyColumn(name = "question_id")
    @Column(name = "selected_option")
    private Map<Long, Integer> selectedAnswers;

    @ElementCollection
    @CollectionTable(name = "correctly_answered_questions", joinColumns = @JoinColumn(name = "progress_id"))
    @Column(name = "question_id")
    private Set<Long> correctlyAnsweredQuestionsId;

    @Enumerated(EnumType.STRING)
    private TestStatus status;

    private LocalDateTime testStartTime;

    @PrePersist
    public void prePersist() {
        this.testStartTime = LocalDateTime.now();
    }

    public  UserTestProgress(User user , Test test) {
        this.user = user;
        this.test = test;
        this.status = TestStatus.IN_PROGRESS;
        this.testStartTime = LocalDateTime.now();
    }


    public UserTestProgress(){}

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

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Map<Long, Integer> getSelectedAnswers() {
        return selectedAnswers;
    }

    public void setSelectedAnswers(Map<Long, Integer> selectedAnswers) {
        this.selectedAnswers = selectedAnswers;
    }

    public TestStatus getStatus() {
        return status;
    }

    public void setStatus(TestStatus status) {
        this.status = status;
    }

    public Set<Long> getCorrectlyAnsweredQuestionsId() {
        return correctlyAnsweredQuestionsId;
    }

    public void setCorrectlyAnsweredQuestionId(Set<Long> correctlyAnsweredQuestionsId) {
        this.correctlyAnsweredQuestionsId = correctlyAnsweredQuestionsId;
    }

    public LocalDateTime getTestStartTime() {
        return testStartTime;
    }

    public void setTestStartTime(LocalDateTime testStartTime) {
        this.testStartTime = testStartTime;
    }

}
