package com.robosoft.elearning.repository;

import com.robosoft.elearning.modal.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    int countBySubjectIdAndIdLessThan(Long subjectId, Long chapterId);
}
