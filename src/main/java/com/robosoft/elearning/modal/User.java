package com.robosoft.elearning.modal;

import com.robosoft.elearning.dto.request.BaseRegisterRequest;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = false)
    private String userName;

    private String profileImageUrl;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserTestResult userTestResult;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCurrentlyStudying> currentlyStudyingSubjects;

    private Float chaptersCompletedInPercentage;

    public User(Long userId) {
    }

    private boolean isNotificationEnabled;

    private String deviceToken;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public User(BaseRegisterRequest registerRequest, String encodedPassword) {
        this.email = registerRequest.getEmail();
        this.password = encodedPassword;
        this.userName = registerRequest.getUserName();
        this.isNotificationEnabled = true;
        if (registerRequest.getRoles() == null || registerRequest.getRoles().isEmpty()) {
            this.roles.add(Role.USER);
        } else {
            this.roles = registerRequest.getRoles();
        }
    }
    User(){}

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public User(String email, String userName, String profileImageUrl, String password, List<Role> roles) {
        this.email = email;
        this.userName = userName;
        this.profileImageUrl = profileImageUrl;
        this.password = password;
        this.roles = roles;
    }

    public User(String email, String userName, String password, List<Role> roles) {
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }

    public UserTestResult getUserTestResult() {
        return userTestResult;
    }

    public void setUserTestResult(UserTestResult userTestResult) {
        this.userTestResult = userTestResult;
    }

    public boolean isNotificationEnabled() {
        return isNotificationEnabled;
    }

    public void setNotificationEnabled(boolean notificationEnabled) {
        isNotificationEnabled = notificationEnabled;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public List<UserCurrentlyStudying> getCurrentlyStudyingSubjects() {
        return currentlyStudyingSubjects;
    }

    public void setCurrentlyStudyingSubjects(List<UserCurrentlyStudying> currentlyStudyingSubjects) {
        this.currentlyStudyingSubjects = currentlyStudyingSubjects;
    }

    public Float getChaptersCompletedInPercentage() {
        return chaptersCompletedInPercentage;
    }

    public void setChaptersCompletedInPercentage(Float chaptersCompletedInPercentage) {
        this.chaptersCompletedInPercentage = chaptersCompletedInPercentage;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles + '\'' +
                ", testResult =" + userTestResult + '\'' +
                ", profileImageUrl=" + profileImageUrl +
                ", isNotificationEnabled" + isNotificationEnabled +
                ", deviceToken" + deviceToken +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
