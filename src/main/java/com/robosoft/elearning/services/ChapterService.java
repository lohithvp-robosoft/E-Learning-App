package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.request.ChapterRequest;
import com.robosoft.elearning.dto.response.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ChapterService {
    ResponseEntity<ResponseDTO<ChapterResponse>> getChapterById(long id);

    ResponseEntity<ResponseDTO<List<ChapterResponse>>> getAllChapters();

    ResponseEntity<ResponseDTO<Map<String, Object>>> getChaptersBySubjectId(Long subjectId, HttpServletRequest request);
    ResponseEntity<ResponseDTO<ChapterResponse>> createChapter(ChapterRequest chapterRequest);
    ResponseEntity<ResponseDTO<ChapterResponse>> updateChapter(Long id, ChapterRequest chapterRequest);
    ResponseEntity<ResponseDTO<Void>> deleteChapter(Long id);
}