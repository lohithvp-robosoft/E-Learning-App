package com.robosoft.elearning.repository;

import com.robosoft.elearning.modal.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
//    List<Content> findByLessonIdOrderByPageNumber(Long lessonId);
Page<Content> findByLessonId(Long lessonId, Pageable pageable);

}
