package com.robosoft.elearning.modal;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.HashMap;
import java.util.HashSet;
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

    private Long currentQuestionId;
    private Double totalScore;
    private Integer totalAnsweredQuestions;
    private Integer totalNumberOfQuestions;

    private Integer lessonIndex;
    private String lessonName;
    private Integer chapterIndex;

    @Enumerated(EnumType.STRING)
    private TestStatus status;

    public  UserTestProgress(User user , Test test, Long questionId,Integer totalNumberOfQuestions, Integer lessonIndex, String lessonName, Integer chapterIndex) {
        this.user = user;
        this.test = test;
        this.totalScore = 0.0;
        this.totalAnsweredQuestions = 0;
        this.status = TestStatus.IN_PROGRESS;
        this.currentQuestionId = questionId;
        this.selectedAnswers = new HashMap<>();
        this.correctlyAnsweredQuestionsId = new HashSet<>();
        this.totalNumberOfQuestions = totalNumberOfQuestions;
        this.lessonIndex = lessonIndex;
        this.lessonName = lessonName;
        this.chapterIndex = chapterIndex;
    }

    public UserTestProgress(){}

    // Getters and setters for the fields
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

    public Long getCurrentQuestionId() {
        return currentQuestionId;
    }

    public void setCurrentQuestionId(Long currentQuestionId) {
        this.currentQuestionId = currentQuestionId;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getTotalAnsweredQuestions() {
        return totalAnsweredQuestions;
    }

    public void setTotalAnsweredQuestions(Integer totalAnsweredQuestions) {
        this.totalAnsweredQuestions = totalAnsweredQuestions;
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

    public Integer getTotalNumberOfQuestions() {
        return totalNumberOfQuestions;
    }

    public void setTotalNumberOfQuestions(Integer totalNumberOfQuestions) {
        this.totalNumberOfQuestions = totalNumberOfQuestions;
    }

    public Integer getLessonIndex() {
        return lessonIndex;
    }

    public void setLessonIndex(Integer lessonIndex) {
        this.lessonIndex = lessonIndex;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public Integer getChapterIndex() {
        return chapterIndex;
    }

    public void setChapterIndex(Integer chapterIndex) {
        this.chapterIndex = chapterIndex;
    }
}
