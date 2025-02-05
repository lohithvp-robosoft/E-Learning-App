package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.request.SubjectRequest;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.SubjectResponse;
import com.robosoft.elearning.dto.response.SubjectResponseList;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.modal.Subject;
import com.robosoft.elearning.modal.User;
import com.robosoft.elearning.repository.*;
import com.robosoft.elearning.services.SubjectService;
import com.robosoft.elearning.util.EntityMapperUtil;
import com.robosoft.elearning.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

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

    @Value("${subject.success.create}")
    private String createSubjectSuccessMessage;

    @Value("${subject.success.update}")
    private String updateSubjectSuccessMessage;

    @Value("${subject.success.delete}")
    private String deleteSubjectSuccessMessage;


    public ResponseEntity<ResponseDTO<SubjectResponseList>> getAllSubjects() {
        List<SubjectResponse> subjectResponses = subjectRepository.findAll().stream()
                .map(entityMapperUtil::convertSubjectToSubjectResponse)
                .toList();
        SubjectResponseList subjectResponseList = new SubjectResponseList();
        subjectResponseList.setSubjects(subjectResponses);

        return responseUtil.successResponse(subjectResponseList, fetchAllSubjectsSuccessMessage);
    }

    @Override
    public ResponseEntity<ResponseDTO<SubjectResponse>> getSubjectById(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(subjectNotFoundMessage));

        SubjectResponse subjectResponse = entityMapperUtil.convertSubjectToSubjectResponse(subject);

        return responseUtil.successResponse(subjectResponse, fetchSubjectByIdSuccessMessage);
    }


    @Override
    public ResponseEntity<ResponseDTO<List<SubjectResponse>>> searchSubjectByName(String name) {
        List<Subject> subjects = subjectRepository.findBySubjectNameContainingIgnoreCase(name);

        if (subjects.isEmpty()) {
            return responseUtil.errorResponse(subjectNotFoundMessage);
        }

        List<SubjectResponse> subjectResponses = subjects.stream()
                .map(entityMapperUtil::convertSubjectToSubjectResponse)
                .toList();

        return responseUtil.successResponse(subjectResponses, null);
    }


    @Override
    public ResponseEntity<ResponseDTO<SubjectResponse>> createSubject(SubjectRequest subjectRequest) {
        Subject subject = new Subject();
        subject.setSubjectName(subjectRequest.getSubjectName());
        subject.setSubjectIcon(subjectRequest.getSubjectIcon());
        Subject savedSubject = subjectRepository.save(subject);
        SubjectResponse subjectResponse = entityMapperUtil.convertSubjectToSubjectResponse(savedSubject);
        return responseUtil.successResponse(subjectResponse, createSubjectSuccessMessage);
    }

    @Override
    public ResponseEntity<ResponseDTO<SubjectResponse>> updateSubject(Long id, SubjectRequest subjectRequest) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(subjectNotFoundMessage));
        subject.setSubjectName(subjectRequest.getSubjectName());
        subject.setSubjectIcon(subjectRequest.getSubjectIcon());
        Subject updatedSubject = subjectRepository.save(subject);
        SubjectResponse subjectResponse = entityMapperUtil.convertSubjectToSubjectResponse(updatedSubject);
        return responseUtil.successResponse(subjectResponse, updateSubjectSuccessMessage);
    }

    @Override
    public ResponseEntity<ResponseDTO<Void>> deleteSubject(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(subjectNotFoundMessage));
        subjectRepository.delete(subject);
        return responseUtil.successResponse(null, deleteSubjectSuccessMessage);
    }

}