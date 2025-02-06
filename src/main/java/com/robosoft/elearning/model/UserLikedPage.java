package com.robosoft.elearning.model;

import jakarta.persistence.*;

@Entity
public class UserLikedPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Topic topic;

    @Column(nullable = false)
    private int pageNumber;

    public UserLikedPage(User user, Topic topic, int pageNumber) {
        this.user = user;
        this.topic = topic;
        this.pageNumber = pageNumber;
    }

    public UserLikedPage(){}

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

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}

