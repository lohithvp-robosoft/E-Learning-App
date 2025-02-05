package com.robosoft.elearning.repository;

import com.robosoft.elearning.modal.ChapterCompleted;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChapterCompletedRepository extends JpaRepository<ChapterCompleted, Long> {
    boolean existsByChapterIdAndUserId(Long chapterId, Long userId);

    long countBySubjectIdAndUserId(Long subjectId, Long userId);

}
