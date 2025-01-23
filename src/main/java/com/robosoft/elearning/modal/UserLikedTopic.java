package com.robosoft.elearning.modal;

import jakarta.persistence.*;

@Entity
public class UserLikedTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Topic topic;

    public UserLikedTopic(User user, Topic topic) {
        this.user = user;
        this.topic = topic;
    }

    public UserLikedTopic(){}

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
}

