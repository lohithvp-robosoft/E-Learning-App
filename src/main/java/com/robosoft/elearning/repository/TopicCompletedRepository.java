package com.robosoft.elearning.repository;


import com.robosoft.elearning.modal.Lesson;
import com.robosoft.elearning.modal.TopicCompleted;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicCompletedRepository extends JpaRepository<TopicCompleted, Long> {
    boolean existsByTopicIdAndUserId(Long topicId, Long userId);
    long countByLessonIdAndUserId(Long lessonId, Long userId);
    long countByLessonAndUserId(Lesson lesson, Long userId);

    long countByTopicIdAndUserId(Long topicId, Long userId);
    Optional<TopicCompleted> findByTopicIdAndUserId(Long topicId, Long userId);
    long countByLessonId(Long lessonId);
}
