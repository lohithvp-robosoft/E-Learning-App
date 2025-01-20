package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.response.LessonResponse;
import com.robosoft.elearning.modal.Lesson;
import com.robosoft.elearning.repository.LessonRepository;
import com.robosoft.elearning.services.LessonService;
import com.robosoft.elearning.util.EntityMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private EntityMapperUtil entityMapperUtil;

    @Override
    public ResponseEntity<LessonResponse> getLessonById(long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
        LessonResponse lessonResponse = entityMapperUtil.convertLessonToLessonResponse(lesson);
        return ResponseEntity.ok(lessonResponse);
    }

    @Override
    public ResponseEntity<LessonResponse> getLessonDetailsById(long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        LessonResponse lessonResponse = entityMapperUtil.convertLessonToLessonResponse(lesson);
        return ResponseEntity.ok(lessonResponse);
    }
}
