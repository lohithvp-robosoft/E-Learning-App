package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.response.ChapterResponse;
import com.robosoft.elearning.dto.response.LessonResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.modal.Chapter;
import com.robosoft.elearning.modal.Lesson;
import com.robosoft.elearning.repository.ChapterRepository;
import com.robosoft.elearning.services.ChapterService;
import com.robosoft.elearning.util.EntityMapperUtil;
import com.robosoft.elearning.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private EntityMapperUtil entityMapperUtil;

    @Autowired
    private ResponseUtil responseUtil;

    @Override
    public ResponseEntity<ResponseDTO<List<ChapterResponse>>> getAllChapters() {
        List<Chapter> chapters = chapterRepository.findAll();
        List<ChapterResponse> responseList = new ArrayList<>();

        // Use constructor without lessons
        chapters.forEach(chapter -> {
            ChapterResponse chapterResponse = new ChapterResponse(
                    chapter.getId(),
                    chapter.getChapterName(),
                    chapter.getChapterImg()
            );
            responseList.add(chapterResponse);
        });

        return responseUtil.successResponse(responseList);
    }

    @Override
    public ResponseEntity<ResponseDTO<ChapterResponse>> getChapterById(long id) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chapter not found"));
        ChapterResponse chapterResponse = entityMapperUtil.convertChapterToChapterResponse(chapter);
        chapterResponse = new ChapterResponse(chapter.getId(),chapter.getChapterName(),chapter.getChapterImg());
        return responseUtil.successResponse(chapterResponse);
    }

    @Override
    public ResponseEntity<ResponseDTO<ChapterResponse>> getChapterWithLessons(long chapterId) {
        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new RuntimeException("Chapter not found"));

        List<LessonResponse> lessonResponses = new ArrayList<>();
        int lessonCounter = 1;
        for (Lesson lesson : chapter.getLessons()) {
            String lessonNumber = "Lesson " + lessonCounter++;
            lessonResponses.add(new LessonResponse(
                    lessonNumber,
                    lesson.getLessonName(),
                    lesson.getLessonImg(),
                    lesson.getLevel(),
                    lesson.getHeading(),
                    lesson.getSubheading()
            ));
        }

        ChapterResponse chapterResponse = new ChapterResponse(chapter.getChapterName(), lessonResponses);
        return responseUtil.successResponse(chapterResponse);
    }


    @Override
    public ResponseEntity<ResponseDTO<List<ChapterResponse>>> getAllChaptersWithLessons() {
        List<Chapter> chapters = chapterRepository.findAll();
        List<ChapterResponse> responseList = new ArrayList<>();

        // For each chapter, fetch its lessons
        chapters.forEach(chapter -> {
            List<LessonResponse> lessonResponses = new ArrayList<>();
            int lessonCounter = 1;
            for (Lesson lesson : chapter.getLessons()) {
                String lessonNumber = "Lesson " + lessonCounter++;
                lessonResponses.add(new LessonResponse(
                        lessonNumber,
                        lesson.getLessonName(),
                        lesson.getLessonImg(),
                        lesson.getLevel(),
                        lesson.getHeading(),
                        lesson.getSubheading()
                ));
            }

            // Add chapter with lessons
            ChapterResponse chapterResponse = new ChapterResponse(
                    chapter.getId(),
                    chapter.getChapterName(),
                    chapter.getChapterImg(),
                    lessonResponses
            );
            responseList.add(chapterResponse);
        });

        return responseUtil.successResponse(responseList);
    }

    @Override
    public ResponseEntity<ResponseDTO<List<ChapterResponse>>> getChaptersBySubjectId(Long subjectId) {
        List<Chapter> chapters = chapterRepository.findBySubjectId(subjectId);
        List<ChapterResponse> responseList = new ArrayList<>();
        chapters.forEach(chapter -> responseList.add(entityMapperUtil.convertChapterToChapterResponse(chapter)));
        return responseUtil.successResponse(responseList);
    }
}