package com.robosoft.elearning.repository;

import com.robosoft.elearning.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserIdOrderByTimestampDesc(Long userId);
    void deleteByUserId(Long userId);
}
