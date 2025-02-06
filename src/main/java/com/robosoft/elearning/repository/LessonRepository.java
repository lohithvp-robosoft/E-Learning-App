package com.robosoft.elearning.repository;

import com.robosoft.elearning.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    int countByChapterIdAndIdLessThan(Long chapterId, Long lessonId);
    long countByChapterId(long chapterId);

    List<Lesson> findByChapterId(Long chapterId);
    Optional<Lesson> findById(Long id);
}
