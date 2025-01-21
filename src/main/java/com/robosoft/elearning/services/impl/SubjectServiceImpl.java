package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.SubjectResponse;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.modal.Subject;
import com.robosoft.elearning.modal.User;
import com.robosoft.elearning.modal.UserCurrentlyStudyingSubject;
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

    @Autowired
    private UserCurrentlyStudyingSubjectRepository userCurrentlyStudyingSubjectRepository;

    @Value("${subject.success.fetch-all}")
    private String fetchAllSubjectsSuccessMessage;

    @Value("${subject.error.not-found}")
    private String subjectNotFoundMessage;

    @Value("${subject.success.fetch-by-id}")
    private String fetchSubjectByIdSuccessMessage;

    @Value("${subject.success.fetch-details}")
    private String fetchSubjectDetailsMessage;


    public ResponseEntity<ResponseDTO<Void>> assignSubjectToUser(HttpServletRequest request, Long subjectId) {
        Long userId = jwtUtils.getUserIdFromRequestHeader(request);
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new RuntimeException("Subject not found"));

        List<UserCurrentlyStudyingSubject> existingSubjects = userCurrentlyStudyingSubjectRepository.findByUserIdAndSubjectId(userId, subjectId);
        if (existingSubjects.isEmpty()) {
            UserCurrentlyStudyingSubject userSubject = new UserCurrentlyStudyingSubject();
            userSubject.setUser(user);
            userSubject.setSubject(subject);
            userSubject.setIsStudying(true);

            userCurrentlyStudyingSubjectRepository.save(userSubject);
            return responseUtil.successResponse(null);
        } else {
            return responseUtil.errorResponse("User is already studying this subject.");
        }
    }

    public ResponseEntity<ResponseDTO<Void>> assignSubjectsToUser(HttpServletRequest request, List<Long> subjectIds) {
        Long userId = jwtUtils.getUserIdFromRequestHeader(request);
        for (Long subjectId : subjectIds) {
            assignSubjectToUser(request, subjectId);
        }
        return responseUtil.successResponse(null);
    }

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

}