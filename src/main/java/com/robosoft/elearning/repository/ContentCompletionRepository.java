package com.robosoft.elearning.repository;

import com.robosoft.elearning.modal.ContentCompletion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentCompletionRepository extends JpaRepository<ContentCompletion, Long> {

    List<ContentCompletion> findByTopicIdAndUserId(Long topicId, Long userId);

    boolean existsByTopicIdAndUserIdAndPageNumber(Long topicId, Long userId, int pageNumber);

    @Query("SELECT c.pageNumber FROM ContentCompletion c WHERE c.topic.id = :topicId AND c.userId = :userId")
    List<Integer> findCompletedPagesByTopicIdAndUserId(Long topicId, Long userId);

}
