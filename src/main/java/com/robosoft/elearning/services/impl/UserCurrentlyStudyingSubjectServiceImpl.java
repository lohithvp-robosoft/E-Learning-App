package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.response.CurrentlyStudyingResponseDTO;
import com.robosoft.elearning.dto.response.SubjectResponse;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.modal.UserCurrentlyStudyingSubject;
import com.robosoft.elearning.repository.UserCurrentlyStudyingSubjectRepository;
import com.robosoft.elearning.services.UserCurrentlyStudyingSubjectService;
import com.robosoft.elearning.util.EntityMapperUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserCurrentlyStudyingSubjectServiceImpl implements UserCurrentlyStudyingSubjectService {
    @Autowired
    private UserCurrentlyStudyingSubjectRepository repository;

    @Autowired
    private EntityMapperUtil entityMapperUtil;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public ResponseEntity<List<CurrentlyStudyingResponseDTO>> getCurrentlyStudyingSubjects(HttpServletRequest request) {
        Long userId = jwtUtils.getUserIdFromRequestHeader(request);
        List<UserCurrentlyStudyingSubject> subjects = repository.findByUserId(userId);

        // Map entities to DTOs using the EntityMapperUtil
        List<CurrentlyStudyingResponseDTO> response = subjects.stream()
                .map(subject -> {
                    // Mapping Subject to SubjectResponse using EntityMapperUtil
                    SubjectResponse subjectResponse = entityMapperUtil.convertSubjectToSubjectResponse(subject.getSubject());

                    // Map to CurrentlyStudyingResponseDTO
                    return new CurrentlyStudyingResponseDTO(
                            subjectResponse.getSubjectName(),
                            subjectResponse.getSubjectIcon(),
                            subject.getCurrentChapter().getChapterName(), // Assuming you want the chapter name
                            subject.getCompletedChapterInPercentage()
                    );
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
