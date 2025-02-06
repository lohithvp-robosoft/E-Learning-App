package com.robosoft.elearning.repository;

import com.robosoft.elearning.model.Test;
import com.robosoft.elearning.model.User;
import com.robosoft.elearning.model.UserTestScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  UserTestScoreRepository extends JpaRepository<UserTestScore, Long> {

    List<UserTestScore> findByUser(User user);
    List<UserTestScore> findByTestAndUser(Test test, User user);
    List<UserTestScore> findByTestIdInAndUser(List<Long> testIds, User user);
}
