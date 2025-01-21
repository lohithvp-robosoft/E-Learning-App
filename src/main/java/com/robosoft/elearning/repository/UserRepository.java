package com.robosoft.elearning.repository;

import com.robosoft.elearning.modal.User;
import com.robosoft.elearning.modal.UserCurrentlyStudyingSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    Optional<User> findById(Long id);
   // Optional<UserCurrentlyStudyingSubject> findByUserId(Long userId);
}
