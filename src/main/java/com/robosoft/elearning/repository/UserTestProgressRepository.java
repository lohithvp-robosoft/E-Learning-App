package com.robosoft.elearning.repository;

import com.robosoft.elearning.model.UserTestProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTestProgressRepository extends JpaRepository<UserTestProgress, Long> {

    Optional<UserTestProgress> findByUserIdAndTestId(Long userId, Long testId);

    List<UserTestProgress> findTestsByStatus(UserTestProgress.TestStatus status);
}
