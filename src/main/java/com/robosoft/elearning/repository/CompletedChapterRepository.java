package com.robosoft.elearning.repository;


import com.robosoft.elearning.modal.CompletedChapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompletedChapterRepository extends JpaRepository<CompletedChapter, Long> {
    List<CompletedChapter> findByUserId(Long userId);
    List<CompletedChapter> findByUserIdAndChapterId(Long userId, Long chapterId);
}
