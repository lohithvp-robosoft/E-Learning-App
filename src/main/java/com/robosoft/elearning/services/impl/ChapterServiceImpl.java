package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.response.ChapterResponse;
import com.robosoft.elearning.dto.response.ChapterSummaryResponse;
import com.robosoft.elearning.dto.response.LessonResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.modal.Chapter;
import com.robosoft.elearning.modal.Lesson;
import com.robosoft.elearning.modal.Subject;
import com.robosoft.elearning.repository.ChapterRepository;
import com.robosoft.elearning.repository.SubjectRepository;
import com.robosoft.elearning.services.ChapterService;
import com.robosoft.elearning.util.EntityMapperUtil;
import com.robosoft.elearning.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private SubjectRepository subjectRepository;

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

    public ResponseEntity<ResponseDTO<Map<String, List<ChapterSummaryResponse>>>> getChaptersBySubjectId(Long subjectId) {
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        if (subject.isEmpty()) {
            throw new NotFoundException("Subject not found with ID: " + subjectId);
        }
        List<ChapterSummaryResponse> chapterResponses = chapterRepository.findBySubjectId(subjectId)
                .stream()
                .map(chapter -> {
                    ChapterSummaryResponse response = new ChapterSummaryResponse();
                    response.setSubjectId(subject.get().getId());
                    response.setSubjectName(subject.get().getSubjectName());
                    response.setChapterName(chapter.getChapterName());
                    response.setChapterImg(chapter.getChapterImg());
                    return response;
                }).toList();
        Map<String, List<ChapterSummaryResponse>> responseMap = new HashMap<>();
        responseMap.put(subject.get().getSubjectName(), chapterResponses);

        return responseUtil.successResponse(responseMap, "Chapters fetched successfully");
    }
}