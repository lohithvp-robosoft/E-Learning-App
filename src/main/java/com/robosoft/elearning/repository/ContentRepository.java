package com.robosoft.elearning.repository;

import com.robosoft.elearning.modal.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
    List<Content> findByLessonIdOrderByPageNumber(Long lessonId);
}
