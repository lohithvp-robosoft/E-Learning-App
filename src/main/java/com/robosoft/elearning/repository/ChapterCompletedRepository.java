package com.robosoft.elearning.repository;

import com.robosoft.elearning.model.ChapterCompleted;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterCompletedRepository extends JpaRepository<ChapterCompleted, Long> {
    boolean existsByChapterIdAndUserId(Long chapterId, Long userId);

    long countBySubjectIdAndUserId(Long subjectId, Long userId);

}
