package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.response.CurrentlyStudyingResponseDTO;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.SubjectResponse;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.modal.Chapter;
import com.robosoft.elearning.modal.Lesson;
import com.robosoft.elearning.modal.User;
import com.robosoft.elearning.modal.UserCurrentlyStudyingSubject;
import com.robosoft.elearning.repository.ChapterRepository;
import com.robosoft.elearning.repository.UserCurrentlyStudyingSubjectRepository;
import com.robosoft.elearning.repository.UserRepository;
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
    private UserCurrentlyStudyingSubjectRepository userCurrentlyStudyingSubjectRepository;

    @Autowired
    private EntityMapperUtil entityMapperUtil;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public ResponseEntity<ResponseDTO<List<CurrentlyStudyingResponseDTO>>> getCurrentlyStudyingSubjects(HttpServletRequest request) {
        Long userId = jwtUtils.getUserIdFromRequestHeader(request);

        List<UserCurrentlyStudyingSubject> subjects = userCurrentlyStudyingSubjectRepository.findByUserId(userId);

        List<CurrentlyStudyingResponseDTO> response = subjects.stream()
                .map(subject -> {
                    SubjectResponse subjectResponse = entityMapperUtil.convertSubjectToSubjectResponse(subject.getSubject());

                    Chapter chapter = subject.getChapter();
                    if (chapter == null) {
                        throw new NotFoundException("Chapter is not assigned for UserCurrentlyStudyingSubject with ID: " + subject.getId());
                    }

                    int totalLessons = chapter.getLessons() != null ? chapter.getLessons().size() : 0;
                    int completedLessons = subject.getCompletedLessonsCount();
                    int completedChapterPercentage = totalLessons > 0
                            ? (int) (((double) completedLessons / totalLessons) * 100)
                            : 0;

                    return new CurrentlyStudyingResponseDTO(
                            subjectResponse.getSubjectName(),
                            subjectResponse.getSubjectIcon(),
                            chapter.getChapterName(),
                            (double) completedChapterPercentage
                    );
                })
                .collect(Collectors.toList());

        return responseUtil.successResponse(response);
    }


    @Override
    public ResponseEntity<ResponseDTO<String>> updateLessonProgress(HttpServletRequest request, Long chapterId, Lesson lessonId, boolean isCompleted) {
        Long userId = jwtUtils.getUserIdFromRequestHeader(request);
        UserCurrentlyStudyingSubject studyingSubject = userCurrentlyStudyingSubjectRepository.findByUserIdAndChapterId(userId, chapterId);

        if (studyingSubject != null) {
            studyingSubject.updateLessonProgress(lessonId, isCompleted);
            userCurrentlyStudyingSubjectRepository.save(studyingSubject);
            return responseUtil.successResponse("Lesson progress updated successfully");
        } else {
            return responseUtil.errorResponse("User is not studying this chapter");
        }
    }

    public void assignChapterToUser(HttpServletRequest request, long chapterId) {
        Long userId = jwtUtils.getUserIdFromRequestHeader(request);

        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new RuntimeException("Chapter not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<UserCurrentlyStudyingSubject> userSubjects = userCurrentlyStudyingSubjectRepository.findByUserId(userId);
        UserCurrentlyStudyingSubject userSubject = null;
        if (!userSubjects.isEmpty()) {
            userSubject = userSubjects.get(0);
        } else {

            userSubject = new UserCurrentlyStudyingSubject();
            userSubject.setUser(user);
        }

        userSubject.setChapter(chapter);

        userCurrentlyStudyingSubjectRepository.save(userSubject);
    }



}

//    @Autowired
//    private UserCurrentlyStudyingSubjectRepository userCurrentlyStudyingSubjectRepository;
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
//        List<UserCurrentlyStudyingSubject> subjects = userCurrentlyStudyingSubjectRepository.findByUserId(userId);
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
