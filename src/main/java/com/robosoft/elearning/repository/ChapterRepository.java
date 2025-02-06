package com.robosoft.elearning.repository;

import com.robosoft.elearning.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    int countBySubjectIdAndIdLessThan(Long subjectId, Long chapterId);

    List<Chapter> findBySubjectId(Long subjectId);

}
