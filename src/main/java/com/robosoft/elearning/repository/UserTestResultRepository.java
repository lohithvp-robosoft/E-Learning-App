package com.robosoft.elearning.repository;

import com.robosoft.elearning.modal.Lesson;
import com.robosoft.elearning.modal.User;
import com.robosoft.elearning.modal.UserTestResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTestResultRepository extends JpaRepository<UserTestResult, Long> {
    Optional<UserTestResult> findByUser(User user);

}
