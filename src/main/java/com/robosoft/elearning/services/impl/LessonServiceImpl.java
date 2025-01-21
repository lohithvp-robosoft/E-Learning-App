package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.response.LessonResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.modal.Lesson;
import com.robosoft.elearning.repository.LessonRepository;
import com.robosoft.elearning.services.LessonService;
import com.robosoft.elearning.util.EntityMapperUtil;
import com.robosoft.elearning.util.ResponseUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private EntityMapperUtil entityMapperUtil;

    @Autowired
    private ResponseUtil responseUtil;

    @Override
    public ResponseEntity<ResponseDTO<LessonResponse>> getLessonById(long id) {
        try {
            Lesson lesson = lessonRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Lesson not found"));
            LessonResponse lessonResponse = entityMapperUtil.convertLessonToLessonResponse(lesson);

            return responseUtil.successResponse(lessonResponse, "Lesson fetched successfully");
        } catch (RuntimeException ex) {
            return responseUtil.errorResponse(ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<LessonResponse>> getLessonDetailsById(long lessonId) {
        try {
            Lesson lesson = lessonRepository.findById(lessonId)
                    .orElseThrow(() -> new RuntimeException("Lesson not found"));
            LessonResponse lessonResponse = entityMapperUtil.convertLessonToLessonResponse(lesson);

            return responseUtil.successResponse(lessonResponse, "Lesson details fetched successfully");
        } catch (RuntimeException ex) {
            return responseUtil.errorResponse(ex.getMessage());
        }
    }

    @Transactional
    @Override
    public ResponseEntity<ResponseDTO<List<LessonResponse>>> getLessonsByChapterId(Long chapterId) {
        List<Lesson> lessons = lessonRepository.findByChapterId(chapterId);
        List<LessonResponse> responseList = new ArrayList<>();
        lessons.forEach(lesson -> responseList.add(entityMapperUtil.convertLessonToLessonResponse(lesson)));
        return responseUtil.successResponse(responseList);
    }
}
