package com.robosoft.elearning.repository;

import com.robosoft.elearning.modal.LessonCompleted;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonCompletedRepository extends JpaRepository<LessonCompleted, Long> {
    boolean existsByLessonIdAndUserId(Long lessonId, Long userId);
    long countByChapterIdAndUserId(Long chapterId, Long userId);
}
