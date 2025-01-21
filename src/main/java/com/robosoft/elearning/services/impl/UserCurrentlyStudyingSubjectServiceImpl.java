package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.response.CurrentlyStudyingResponseDTO;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.SubjectResponse;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.modal.UserCurrentlyStudyingSubject;
import com.robosoft.elearning.repository.UserCurrentlyStudyingSubjectRepository;
import com.robosoft.elearning.services.UserCurrentlyStudyingSubjectService;
import com.robosoft.elearning.util.EntityMapperUtil;
import com.robosoft.elearning.util.ResponseUtil;
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

    @Autowired
    private ResponseUtil responseUtil;

    @Override
    public ResponseEntity<ResponseDTO<List<CurrentlyStudyingResponseDTO>>> getCurrentlyStudyingSubjects(HttpServletRequest request) {
        Long userId = jwtUtils.getUserIdFromRequestHeader(request);

        // Retrieve all subjects the user is currently studying
        List<UserCurrentlyStudyingSubject> subjects = repository.findByUserId(userId);

        // Map the subjects to the response DTO
        List<CurrentlyStudyingResponseDTO> response = subjects.stream()
                .map(subject -> {
                    SubjectResponse subjectResponse = entityMapperUtil.convertSubjectToSubjectResponse(subject.getSubject());

                    // Calculate the chapter completion percentage
                    int totalLessons = subject.getChapter().getLessons().size();
                    int completedLessons = subject.getCompletedLessonsCount();
                    int completedChapterPercentage = (int) (((double) completedLessons / totalLessons) * 100);

                    // Return the response DTO with the necessary details
                    return new CurrentlyStudyingResponseDTO(
                            subjectResponse.getSubjectName(),
                            subjectResponse.getSubjectIcon(),
                           subject.getChapter().getTitle(),
                            (double) completedChapterPercentage
                    );
                })
                .collect(Collectors.toList());

        return responseUtil.successResponse(response);
    }

    @Override
    public ResponseEntity<ResponseDTO<String>> updateLessonProgress(HttpServletRequest request, Long chapterId, Long lessonId, boolean isCompleted) {
        Long userId = jwtUtils.getUserIdFromRequestHeader(request);

        // Retrieve the user's progress on the subject
        UserCurrentlyStudyingSubject studyingSubject = repository.findByUserIdAndChapterId(userId, chapterId);

        if (studyingSubject != null) {
            // Update the lesson progress and check for chapter completion
            studyingSubject.updateLessonProgress(lessonId, isCompleted);

            // Save the updated progress
            repository.save(studyingSubject);

            return responseUtil.successResponse("Lesson progress updated successfully");
        } else {
            return responseUtil.errorResponse("User is not studying this chapter");
        }
    }
}

//    @Autowired
//    private UserCurrentlyStudyingSubjectRepository repository;
//
//    @Autowired
//    private EntityMapperUtil entityMapperUtil;
//
//    @Autowired
//    private JwtUtils jwtUtils;
//
//    @Autowired
//    private ResponseUtil responseUtil;
//
//    @Override
//    public ResponseEntity<ResponseDTO<List<CurrentlyStudyingResponseDTO>>> getCurrentlyStudyingSubjects(HttpServletRequest request) {
//        Long userId = jwtUtils.getUserIdFromRequestHeader(request);
//        List<UserCurrentlyStudyingSubject> subjects = repository.findByUserId(userId);
//
//        List<CurrentlyStudyingResponseDTO> response = subjects.stream()
//                .map(subject -> {
//                    SubjectResponse subjectResponse = entityMapperUtil.convertSubjectToSubjectResponse(subject.getSubject());
//                    return new CurrentlyStudyingResponseDTO(
//                            subjectResponse.getSubjectName(),
//                            subjectResponse.getSubjectIcon(),
//                            subject.getCurrentChapter().getChapterName(),
//                            subject.getCompletedChapterInPercentage()
//                    );
//                })
//                .collect(Collectors.toList());
//
//        return responseUtil.successResponse(response);
//    }
//}
