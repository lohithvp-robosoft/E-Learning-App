package com.robosoft.elearning.repository;

import com.robosoft.elearning.modal.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    int countByChapterIdAndIdLessThan(Long chapterId, Long lessonId);
}
