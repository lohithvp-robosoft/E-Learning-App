package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.response.ChapterResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ChapterService {
    ResponseEntity<ResponseDTO<ChapterResponse>> getChapterById(long id);
  //  ResponseEntity<ResponseDTO<ChapterResponse>> getChapterWithLessons(long chapterId);
    ResponseEntity<ResponseDTO<List<ChapterResponse>>> getChaptersBySubjectId(Long subjectId);
    ResponseEntity<ResponseDTO<List<ChapterResponse>>> getAllChapters();

  //  ResponseEntity<ResponseDTO<List<ChapterResponse>>> getAllChaptersWithLessons();

}
