package com.robosoft.elearning.repository;


import com.robosoft.elearning.modal.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContentRepository extends JpaRepository<Content, Long> {

    Page<Content> findByLessonId(Long lessonId, Pageable pageable);
    Page<Content> findByLessonIdAndTopicId(Long lessonId, Long topicId, Pageable pageable);

}
