package com.robosoft.elearning.repository;

import com.robosoft.elearning.modal.Lesson;
import com.robosoft.elearning.modal.Topic;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {

      long countByLesson(Lesson lesson);
      List<Topic> findByLessonId(Long lessonId);
      Page<Topic> findByLessonId(Long lessonId, Pageable pageable);
}
