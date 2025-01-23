package com.robosoft.elearning.modal;

import jakarta.persistence.*;

@Entity
<<<<<<<< HEAD:src/main/java/com/robosoft/elearning/modal/LessonProgress.java
public class LessonProgress {
========
public class UserLikedTopic {
>>>>>>>> 71374221b38073b11727953bb5ef36e203ca27c0:src/main/java/com/robosoft/elearning/modal/UserLikedTopic.java
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
<<<<<<<< HEAD:src/main/java/com/robosoft/elearning/modal/LessonProgress.java
    private Lesson lesson;
========
    private User user;

    @ManyToOne
    private Topic topic;

    public UserLikedTopic(User user, Topic topic) {
        this.user = user;
        this.topic = topic;
    }

    public UserLikedTopic(){}
>>>>>>>> 71374221b38073b11727953bb5ef36e203ca27c0:src/main/java/com/robosoft/elearning/modal/UserLikedTopic.java

    private boolean isCompleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

<<<<<<<< HEAD:src/main/java/com/robosoft/elearning/modal/LessonProgress.java
    public Lesson getLesson() {
        return lesson;
========
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Topic getTopic() {
        return topic;
>>>>>>>> 71374221b38073b11727953bb5ef36e203ca27c0:src/main/java/com/robosoft/elearning/modal/UserLikedTopic.java
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}

