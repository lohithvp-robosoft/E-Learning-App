package com.robosoft.elearning.repository;

import com.robosoft.elearning.modal.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByChapterId(Long chapterId);
}
