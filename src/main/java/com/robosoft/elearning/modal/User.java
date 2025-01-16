package com.robosoft.elearning.modal;

import com.robosoft.elearning.dto.request.BaseRegisterRequest;
import com.robosoft.elearning.dto.request.UserRegisterRequest;
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

    @Column(nullable = false, unique = true)
    private String username;

    private String imageUrl;

    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

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
        this.username = registerRequest.getUserName();

        if (registerRequest.getRoles() == null || registerRequest.getRoles().isEmpty()) {
//            log.info("No roles provided, setting default role");
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

    public String getUsername() {
        return username;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public User(String email, String username, String imageUrl, String password, List<Role> roles) {
        this.email = email;
        this.username = username;
        this.imageUrl = imageUrl;
        this.password = password;
        this.roles = roles;
    }

    public User(String email, String username, String password, List<Role> roles) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles + '\'' +
                ", imageUrl=" + imageUrl +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
