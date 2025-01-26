package com.robosoft.elearning.repository;

import com.robosoft.elearning.modal.Topic;
import com.robosoft.elearning.modal.User;
import com.robosoft.elearning.modal.UserLikedTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLikedTopicRepository extends JpaRepository<UserLikedTopic, Long> {

    UserLikedTopic findByUserAndTopic(User user, Topic topic);
    List<UserLikedTopic> findByUser(User user);
    boolean existsByUserIdAndTopicId(Long userId, Long topicId);
}
