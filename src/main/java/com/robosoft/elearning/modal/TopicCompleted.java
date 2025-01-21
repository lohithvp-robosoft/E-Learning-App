package com.robosoft.elearning.modal;

import jakarta.persistence.*;

@Entity
public class TopicCompleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long topicId;
    private Long userId;

//    @ManyToOne
//    @JoinColumn(name = "lesson_id")
//    private Lesson lesson;

    public TopicCompleted(Long topicId, Long userId) {
        this.topicId = topicId;
        this.userId = userId;
//        this.lesson = lesson;
    }

    public TopicCompleted(){}

    public Long getId() {
        return id;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}