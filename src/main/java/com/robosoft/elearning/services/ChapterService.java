package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.response.ChapterResponse;
import org.springframework.http.ResponseEntity;

public interface ChapterService {
    ResponseEntity<ChapterResponse> getChapterById(long id);
    ResponseEntity<ChapterResponse> getChapterWithLessons(long chapterId);
}
