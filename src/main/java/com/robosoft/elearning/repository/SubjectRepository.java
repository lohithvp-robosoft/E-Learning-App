package com.robosoft.elearning.repository;

import com.robosoft.elearning.modal.Subject;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findBySubjectName(String subjectName);
    @Query("SELECT s FROM Subject s WHERE LOWER(s.subjectName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Subject> findBySubjectNameContainingIgnoreCase(@Param("keyword") String keyword);
}

