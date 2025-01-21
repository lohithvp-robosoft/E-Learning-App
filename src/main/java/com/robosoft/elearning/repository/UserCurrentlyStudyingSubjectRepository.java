package com.robosoft.elearning.repository;

import com.robosoft.elearning.modal.UserCurrentlyStudyingSubject;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCurrentlyStudyingSubjectRepository extends JpaRepository<UserCurrentlyStudyingSubject, Long> {
    List<UserCurrentlyStudyingSubject> findByUserId(Long userId);
    List<UserCurrentlyStudyingSubject> findByUserIdAndSubjectId(Long userId, Long subjectId);
    UserCurrentlyStudyingSubject findByUserIdAndChapterId(Long userId, Long chapterId);
    @EntityGraph(attributePaths = {"chapter"})
    Optional<UserCurrentlyStudyingSubject> findById(Long id);
}