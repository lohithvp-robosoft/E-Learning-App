package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.UserTestScoreResponse;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.modal.*;
import com.robosoft.elearning.repository.ChapterRepository;
import com.robosoft.elearning.repository.LessonRepository;
import com.robosoft.elearning.repository.UserTestScoreRepository;
import com.robosoft.elearning.services.ResultServices;
import com.robosoft.elearning.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @Override
//    public ResponseEntity<ResponseDTO<List<UserTestScoreResponse>>> getAllTestScoreOfAUser(HttpServletRequest result) {
//        User user = jwtUtils.getUserDataFromRequest(result);
//        List<UserTestScore> userTestScoreList = userTestScoreRepository.findByUser(user);
//        List<UserTestScoreResponse> userTestScoreResponsesList= new ArrayList<>();
//
//        for(UserTestScore userTestScore : userTestScoreList){
//         Lesson lesson = userTestScore.getTest().getLesson();
//         String lessonName = lesson.getLessonName();
//         Chapter chapter = lesson.getChapter();
//         Integer lessonIndex = lessonRepository.countByChapterIdAndIdLessThan(chapter.getId(), lesson.getId());
//         String subjectName = chapter.getSubject().getSubjectName();
//         UserTestScoreResponse userTestScoreResponse = new UserTestScoreResponse(userTestScore.getId(),subjectName,lessonIndex,lessonName,userTestScore.getTotalCorrectAnswers(),userTestScore.getTotalAnsweredQuestions(),userTestScore.getTotalNumberOfQuestion(),userTestScore.getTotalMarks());
//         userTestScoreResponsesList.add(userTestScoreResponse);
//        }
//        return responseUtil.successResponse(userTestScoreResponsesList);
//    }


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

//    @Override
//    public ResponseEntity<ResponseDTO<List<UserTestScoreResponse>>> getAllScoreOfAUserBySubjectId(Long subjectId, HttpServletRequest request) {
//        User user = jwtUtils.getUserDataFromRequest(request);
//
//        List<Chapter> chapters = chapterRepository.findBySubjectId(subjectId);
//
//        List<UserTestScore> userTestScoreList = new ArrayList<>();
//
//        for (Chapter chapter : chapters) {
//            for (Lesson lesson : chapter.getLessons()) {
//
//                List<Test> tests = lesson.getTests();
//
//                for (Test test : tests) {
//                    List<UserTestScore> userTestScoresForTest = userTestScoreRepository.findByTestAndUser(test, user);
//                    userTestScoreList.addAll(userTestScoresForTest);
//                }
//            }
//        }
//        if(userTestScoreList.isEmpty()){
//            throw new NotFoundException("User Test Result Not Found");
//        }
//
//        List<UserTestScoreResponse> userTestScoreResponsesList = new ArrayList<>();
//
//        Map<Long, Map<Long, Integer>> lessonIndexCache = new HashMap<>();
//
//
//        for (UserTestScore userTestScore : userTestScoreList) {
//            Lesson lesson = userTestScore.getTest().getLesson();
//            Chapter chapter = lesson.getChapter();
//            String lessonName = lesson.getLessonName();
//            String subjectName = chapter.getSubject().getSubjectName();
//
//            Map<Long, Integer> chapterCache = lessonIndexCache.computeIfAbsent(chapter.getId(), k -> new HashMap<>());
//            Integer lessonIndex = chapterCache.get(lesson.getId());
//
//            if (lessonIndex == null) {
//                lessonIndex = lessonRepository.countByChapterIdAndIdLessThan(chapter.getId(), lesson.getId()) + 1;
//                chapterCache.put(lesson.getId(), lessonIndex);
//            }
//
//            UserTestScoreResponse userTestScoreResponse = new UserTestScoreResponse(
//                    userTestScore.getId(),
//                    subjectName,
//                    lessonIndex,
//                    lessonName,
//                    userTestScore.getTotalCorrectAnswers(),
//                    userTestScore.getTotalAnsweredQuestions(),
//                    userTestScore.getTotalNumberOfQuestion(),
//                    userTestScore.getTotalMarks(),
//                    userTestScore.getCreatedAt()
//            );
//
//            userTestScoreResponsesList.add(userTestScoreResponse);
//        }
//
//        return responseUtil.successResponse(userTestScoreResponsesList);
//    }


    @Override
    public ResponseEntity<ResponseDTO<List<UserTestScoreResponse>>> getAllScoreOfAUserBySubjectId(Long subjectId, HttpServletRequest request) {
        // Get the user from the JWT token in the request
        User user = jwtUtils.getUserDataFromRequest(request);

        // Fetch all chapters related to the subjectId
        List<Chapter> chapters = chapterRepository.findBySubjectId(subjectId);

        // Gather all the tests in bulk
        List<Long> testIds = new ArrayList<>();
        for (Chapter chapter : chapters) {
            for (Lesson lesson : chapter.getLessons()) {
                for (Test test : lesson.getTests()) {
                    testIds.add(test.getId());
                }
            }
        }

        // Fetch all the UserTestScores for the tests in bulk (instead of querying one by one)
        List<UserTestScore> userTestScores = userTestScoreRepository.findByTestIdInAndUser(testIds, user);

        if (userTestScores.isEmpty()) {
            throw new NotFoundException("User Test Result Not Found");
        }

        // Prepare the response list
        List<UserTestScoreResponse> userTestScoreResponsesList = new ArrayList<>();

        // Cache lesson indices
        Map<Long, Map<Long, Integer>> lessonIndexCache = new HashMap<>();

        // Process the UserTestScores and convert them to response objects
        for (UserTestScore userTestScore : userTestScores) {
            Lesson lesson = userTestScore.getTest().getLesson();
            Chapter chapter = lesson.getChapter();
            String lessonName = lesson.getLessonName();
            String subjectName = chapter.getSubject().getSubjectName();

            // Use the cache to get the lesson index
            Map<Long, Integer> chapterCache = lessonIndexCache.computeIfAbsent(chapter.getId(), k -> new HashMap<>());
            Integer lessonIndex = chapterCache.get(lesson.getId());

            if (lessonIndex == null) {
                // Calculate the lesson index (this is still a database query but only done once per lesson)
                lessonIndex = lessonRepository.countByChapterIdAndIdLessThan(chapter.getId(), lesson.getId()) + 1;
                chapterCache.put(lesson.getId(), lessonIndex);
            }

            // Create the response object and add it to the list
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

        // Return the success response
        return responseUtil.successResponse(userTestScoreResponsesList);
    }



}
