package com.robosoft.elearning.repository;

import com.robosoft.elearning.modal.UserCurrentlyStudying;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCurrentlyStudyingRepository extends JpaRepository<UserCurrentlyStudying, Long> {
    Optional<UserCurrentlyStudying> findByUserIdAndSubjectId(Long userId, Long subjectId);

    Optional<UserCurrentlyStudying> findByUserIdAndCurrentTopicId(Long userId, Long topicId);

    List<UserCurrentlyStudying> findAllByUserId(Long userId);

    List<UserCurrentlyStudying> findAllByUserIdAndSubjectSubjectNameContainingIgnoreCase(Long userId, String subjectName);

}

