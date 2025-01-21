package com.robosoft.elearning.repository;

import com.robosoft.elearning.modal.Lesson;
import com.robosoft.elearning.modal.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {

//    long countByLessonId(Long lessonId);
      long countByLesson(Lesson lesson);
}
