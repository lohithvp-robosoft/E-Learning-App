package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.response.ChapterResponse;
import com.robosoft.elearning.dto.response.LessonResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.SubjectResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SubjectService {
    ResponseEntity<ResponseDTO<List<SubjectResponse>>> getAllSubjects();
    ResponseEntity<ResponseDTO<SubjectResponse>> getSubjectById(Long id);
    ResponseEntity<ResponseDTO<SubjectResponse>> searchSubjectByName(String name);
    ResponseEntity<ResponseDTO<List<ChapterResponse>>> getChaptersBySubjectId(Long subjectId);
    ResponseEntity<ResponseDTO<List<LessonResponse>>> getLessonsByChapterId(Long chapterId);


}
