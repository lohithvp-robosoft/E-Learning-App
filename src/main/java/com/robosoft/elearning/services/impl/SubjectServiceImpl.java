package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.response.ChapterResponse;
import com.robosoft.elearning.dto.response.LessonResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.SubjectResponse;
import com.robosoft.elearning.modal.Chapter;
import com.robosoft.elearning.modal.Lesson;
import com.robosoft.elearning.modal.Subject;
import com.robosoft.elearning.repository.ChapterRepository;
import com.robosoft.elearning.repository.LessonRepository;
import com.robosoft.elearning.repository.SubjectRepository;
import com.robosoft.elearning.services.SubjectService;
import com.robosoft.elearning.util.EntityMapperUtil;
import com.robosoft.elearning.util.ResponseUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Component
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private EntityMapperUtil entityMapperUtil;

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Value("${subject.success.fetch-all}")
    private String fetchAllSubjectsSuccessMessage;

    @Value("${subject.error.not-found}")
    private String subjectNotFoundMessage;

    @Value("${subject.success.fetch-by-id}")
    private String fetchSubjectByIdSuccessMessage;

    @Value("${subject.success.fetch-details}")
    private String fetchSubjectDetailsMessage;

    @Override
    public ResponseEntity<ResponseDTO<List<SubjectResponse>>> getAllSubjects() {
        List<SubjectResponse> subjectResponses = subjectRepository.findAll().stream()
                .map(entityMapperUtil::convertSubjectToSubjectResponse)
                .toList();

        return responseUtil.successResponse(subjectResponses, fetchAllSubjectsSuccessMessage);
    }

    @Override
    public ResponseEntity<ResponseDTO<SubjectResponse>> getSubjectById(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(subjectNotFoundMessage));

        SubjectResponse subjectResponse = entityMapperUtil.convertSubjectToSubjectResponse(subject);

        return responseUtil.successResponse(subjectResponse, fetchSubjectByIdSuccessMessage);
    }


    @Override
    public ResponseEntity<ResponseDTO<SubjectResponse>> searchSubjectByName(String name) {
        Subject subject = subjectRepository.findBySubjectName(name)
                .orElseThrow(() -> new RuntimeException("Subject not found with name: " + name));


        if (subject == null) {
            return responseUtil.errorResponse("Resource not found", 404);
        }

        SubjectResponse subjectResponse = entityMapperUtil.convertSubjectToSubjectResponse(subject);
        return responseUtil.successResponse(subjectResponse, fetchSubjectDetailsMessage);
    }

    @Override
    public ResponseEntity<ResponseDTO<List<ChapterResponse>>> getChaptersBySubjectId(Long subjectId) {
        List<Chapter> chapters = chapterRepository.findBySubjectId(subjectId);
        List<ChapterResponse> responseList = new ArrayList<>();
        chapters.forEach(chapter -> responseList.add(entityMapperUtil.convertChapterToChapterResponse(chapter)));
        return responseUtil.successResponse(responseList);
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