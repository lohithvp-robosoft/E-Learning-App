package com.robosoft.elearning.repository;

import com.robosoft.elearning.modal.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findBySubjectId(Long subjectId);
}
