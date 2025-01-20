package com.robosoft.elearning.repository;


import com.robosoft.elearning.modal.Chapter;
import com.robosoft.elearning.modal.CompletedChapter;
import com.robosoft.elearning.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompletedChapterRepository extends JpaRepository<CompletedChapter, Long> {
    List<CompletedChapter> findByUserId(Long userId);
    List<CompletedChapter> findByUserIdAndChapterId(Long userId, Long chapterId);
    Optional<CompletedChapter> findByUserAndChapter(User user, Chapter chapter);
}
