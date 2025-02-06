package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.UserTestScoreResponse;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.model.*;
import com.robosoft.elearning.repository.ChapterRepository;
import com.robosoft.elearning.repository.LessonRepository;
import com.robosoft.elearning.repository.UserTestScoreRepository;
import com.robosoft.elearning.services.ResultServices;
import com.robosoft.elearning.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResultServicesImpl implements ResultServices {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserTestScoreRepository userTestScoreRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private ChapterRepository chapterRepository;

    @Value("${testResult.error.not-found}")
    private String testResultNotFound;


    @Override
    public ResponseEntity<ResponseDTO<List<UserTestScoreResponse>>> getAllScoreOfAUser(HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);

        List<UserTestScore> userTestScoreList = userTestScoreRepository.findByUser(user);
        List<UserTestScoreResponse> userTestScoreResponsesList = new ArrayList<>();

        Map<Long, Map<Long, Integer>> lessonIndexCache = new HashMap<>();

        for (UserTestScore userTestScore : userTestScoreList) {
            Lesson lesson = userTestScore.getTest().getLesson();
            Chapter chapter = lesson.getChapter();
            String lessonName = lesson.getLessonName();
            String subjectName = chapter.getSubject().getSubjectName();

            Map<Long, Integer> chapterCache = lessonIndexCache.computeIfAbsent(chapter.getId(), k -> new HashMap<>());
            Integer lessonIndex = chapterCache.get(lesson.getId());

            if (lessonIndex == null) {
                lessonIndex = lessonRepository.countByChapterIdAndIdLessThan(chapter.getId(), lesson.getId()) + 1;
                chapterCache.put(lesson.getId(), lessonIndex);
            }

            UserTestScoreResponse userTestScoreResponse = new UserTestScoreResponse(
                    userTestScore.getId(),
                    subjectName,
                    lessonIndex,
                    lessonName,
                    userTestScore.getTotalCorrectAnswers(),
                    userTestScore.getTotalAnsweredQuestions(),
                    userTestScore.getTotalNumberOfQuestion(),
                    userTestScore.getTotalMarks(),
                    userTestScore.getCreatedAt()
            );

            userTestScoreResponsesList.add(userTestScoreResponse);
        }

        return responseUtil.successResponse(userTestScoreResponsesList);
    }

    @Override
    public ResponseEntity<ResponseDTO<List<UserTestScoreResponse>>> getAllScoreOfAUserBySubjectId(Long subjectId, HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);

        List<Long> testIds = getTestIdsBySubjectId(subjectId);

        List<UserTestScore> userTestScores = getUserTestScores(testIds, user);

        if (userTestScores.isEmpty()) {
            throw new NotFoundException(testResultNotFound);
        }

        List<UserTestScoreResponse> userTestScoreResponsesList = mapUserTestScoresToResponse(userTestScores);

        return responseUtil.successResponse(userTestScoreResponsesList);
    }

    private List<Long> getTestIdsBySubjectId(Long subjectId) {
        List<Long> testIds = new ArrayList<>();
        List<Chapter> chapters = chapterRepository.findBySubjectId(subjectId);

        for (Chapter chapter : chapters) {
            for (Lesson lesson : chapter.getLessons()) {
                for (Test test : lesson.getTests()) {
                    testIds.add(test.getId());
                }
            }
        }

        return testIds;
    }

    private List<UserTestScore> getUserTestScores(List<Long> testIds, User user) {
        return userTestScoreRepository.findByTestIdInAndUser(testIds, user);
    }

    private List<UserTestScoreResponse> mapUserTestScoresToResponse(List<UserTestScore> userTestScores) {
        List<UserTestScoreResponse> userTestScoreResponsesList = new ArrayList<>();
        Map<Long, Map<Long, Integer>> lessonIndexCache = new HashMap<>();

        for (UserTestScore userTestScore : userTestScores) {
            UserTestScoreResponse response = createUserTestScoreResponse(userTestScore, lessonIndexCache);
            userTestScoreResponsesList.add(response);
        }

        return userTestScoreResponsesList;
    }

    private UserTestScoreResponse createUserTestScoreResponse(UserTestScore userTestScore, Map<Long, Map<Long, Integer>> lessonIndexCache) {
        Lesson lesson = userTestScore.getTest().getLesson();
        Chapter chapter = lesson.getChapter();
        String lessonName = lesson.getLessonName();
        String subjectName = chapter.getSubject().getSubjectName();

        Integer lessonIndex = getLessonIndex(lesson, chapter, lessonIndexCache);

        return new UserTestScoreResponse(
                userTestScore.getId(),
                subjectName,
                lessonIndex,
                lessonName,
                userTestScore.getTotalCorrectAnswers(),
                userTestScore.getTotalAnsweredQuestions(),
                userTestScore.getTotalNumberOfQuestion(),
                userTestScore.getTotalMarks(),
                userTestScore.getCreatedAt()
        );
    }

    private Integer getLessonIndex(Lesson lesson, Chapter chapter, Map<Long, Map<Long, Integer>> lessonIndexCache) {
        Map<Long, Integer> chapterCache = lessonIndexCache.computeIfAbsent(chapter.getId(), k -> new HashMap<>());
        Integer lessonIndex = chapterCache.get(lesson.getId());

        if (lessonIndex == null) {
            lessonIndex = lessonRepository.countByChapterIdAndIdLessThan(chapter.getId(), lesson.getId()) + 1;
            chapterCache.put(lesson.getId(), lessonIndex);
        }

        return lessonIndex;
    }
}
