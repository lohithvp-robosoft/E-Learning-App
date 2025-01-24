package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.response.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ChapterService {
    ResponseEntity<ResponseDTO<ChapterResponse>> getChapterById(long id);
    //  ResponseEntity<ResponseDTO<ChapterResponse>> getChapterWithLessons(long chapterId);
    ResponseEntity<ResponseDTO<List<ChapterResponse>>> getAllChapters();
//    ResponseEntity<ResponseDTO<Map<String, List<ChapterSummaryResponse>>>> getChaptersBySubjectId(Long subjectId);
    //  ResponseEntity<ResponseDTO<List<ChapterResponse>>> getAllChaptersWithLessons();
ResponseEntity<ResponseDTO<CurrentlyStudyingSubjectResponse>> getChaptersDetailsBySubjectId(Long subjectId, HttpServletRequest request);
}