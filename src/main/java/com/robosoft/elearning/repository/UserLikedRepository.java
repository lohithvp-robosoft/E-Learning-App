package com.robosoft.elearning.repository;

import com.robosoft.elearning.modal.UserLiked;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLikedRepository extends JpaRepository<UserLiked, Long> {
    boolean existsByUserIdAndLessonId(Long userId, Long lessonId);
    Optional<UserLiked> findByUserIdAndLessonId(Long userId, Long lessonId);
}
