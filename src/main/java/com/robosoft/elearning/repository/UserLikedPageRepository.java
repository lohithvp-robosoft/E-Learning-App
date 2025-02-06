package com.robosoft.elearning.repository;

import com.robosoft.elearning.model.Topic;
import com.robosoft.elearning.model.User;
import com.robosoft.elearning.model.UserLikedPage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLikedPageRepository  extends JpaRepository<UserLikedPage, Long> {

    UserLikedPage findByUserAndTopic(User user, Topic topic);
    List<UserLikedPage> findByUser(User user);
    boolean existsByUserIdAndTopicId(Long userId, Long topicId);

    UserLikedPage findByUserAndTopicAndPageNumber(User user, Topic topic, int pageNumber);
    boolean existsByUserIdAndTopicIdAndPageNumber(Long userId, Long topicId, int pageNumber);
}
