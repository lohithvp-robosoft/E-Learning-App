package com.robosoft.elearning.modal;

import jakarta.persistence.*;

@Entity
public class UserTestScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserTestResult userTestResult;

    @ManyToOne
    private Test test;

    private Integer totalAnsweredQuestions;
    private Double totalMarks;

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

    public Double getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Double totalMarks) {
        this.totalMarks = totalMarks;
    }
}
