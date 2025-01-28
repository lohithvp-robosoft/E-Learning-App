package com.robosoft.elearning.repository;

import com.robosoft.elearning.modal.Test;
import com.robosoft.elearning.modal.User;
import com.robosoft.elearning.modal.UserTestScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  UserTestScoreRepository extends JpaRepository<UserTestScore, Long> {

    List<UserTestScore> findByUser(User user);
    List<UserTestScore> findByTestAndUser(Test test, User user);
    List<UserTestScore> findByTestIdInAndUser(List<Long> testIds, User user);
    boolean existsByUserIdAndTestId(Long userId, Long testId);
}
