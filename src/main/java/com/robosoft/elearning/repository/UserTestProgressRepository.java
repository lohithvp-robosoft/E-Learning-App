package com.robosoft.elearning.repository;

import com.robosoft.elearning.modal.UserTestProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTestProgressRepository extends JpaRepository<UserTestProgress, Long> {

    Optional<UserTestProgress> findByUserIdAndTestId(Long userId, Long testId);
}
