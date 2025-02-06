package com.robosoft.elearning.repository;


import com.robosoft.elearning.model.Content;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ContentRepository extends JpaRepository<Content, Long> {

    Page<Content> findByLessonId(Long lessonId, Pageable pageable);
    Page<Content> findByLessonIdAndTopicId(Long lessonId, Long topicId, Pageable pageable);

    @Query("SELECT COUNT(DISTINCT c.pageNumber) FROM Content c WHERE c.topic.id = :topicId")
    long countTotalPagesByTopicId(@Param("topicId") Long topicId);

    List<Content> findByTopicIdAndPageNumber(Long topicId, int pageNumber);


}
