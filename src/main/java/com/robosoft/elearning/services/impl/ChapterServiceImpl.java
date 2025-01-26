package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.request.ChapterRequest;
import com.robosoft.elearning.dto.response.*;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.modal.*;
import com.robosoft.elearning.repository.ChapterRepository;
import com.robosoft.elearning.repository.SubjectRepository;
import com.robosoft.elearning.repository.UserCurrentlyStudyingRepository;
import com.robosoft.elearning.services.ChapterService;
import com.robosoft.elearning.util.EntityMapperUtil;
import com.robosoft.elearning.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserCurrentlyStudyingRepository userCurrentlyStudyingRepository;

    @Override
    public ResponseEntity<ResponseDTO<List<ChapterResponse>>> getAllChapters() {
        List<Chapter> chapters = chapterRepository.findAll();
        List<ChapterResponse> responseList = new ArrayList<>();
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


    public ResponseEntity<ResponseDTO<Map<String, Object>>> getChaptersBySubjectId(Long subjectId, HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new NotFoundException("Subject not found with ID: " + subjectId));
        Optional<UserCurrentlyStudying> userCurrentlyStudyingOpt = userCurrentlyStudyingRepository.findByUserIdAndSubjectId(user.getId(), subjectId);
        Long currentChapterId = userCurrentlyStudyingOpt.map(UserCurrentlyStudying::getCurrentChapter)
                .map(Chapter::getId)
                .orElse(null);
        List<ChapterSummaryResponse> chapterResponses = chapterRepository.findBySubjectId(subjectId)
                .stream()
                .map(chapter -> new ChapterSummaryResponse(
                        chapter.getId(),
                        chapter.getChapterName(),
                        chapter.getChapterImg(),
                        chapter.getId().equals(currentChapterId),
                        subject.getId(),
                        subject.getSubjectName()
                ))
                .collect(Collectors.toList());
        Map<String, Object> subjectData = new HashMap<>();
        subjectData.put("chapters", chapterResponses);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("subjectName", subject.getSubjectName());
        responseMap.put("chapters", chapterResponses);
        return responseUtil.successResponse(responseMap, "Chapters fetched successfully");
    }





    @Override
    public ResponseEntity<ResponseDTO<ChapterResponse>> createChapter(ChapterRequest chapterRequest) {
        Chapter chapter = new Chapter();
        chapter.setChapterName(chapterRequest.getChapterName());
        chapter.setChapterImg(chapterRequest.getChapterImg());
        Chapter savedChapter = chapterRepository.save(chapter);
        ChapterResponse chapterResponse = new ChapterResponse(savedChapter.getId(), savedChapter.getChapterName(), savedChapter.getChapterImg());
        return responseUtil.successResponse(chapterResponse, "Chapter created successfully");
    }

    @Override
    public ResponseEntity<ResponseDTO<ChapterResponse>> updateChapter(Long id, ChapterRequest chapterRequest) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chapter not found with ID: " + id));
        chapter.setChapterName(chapterRequest.getChapterName());
        chapter.setChapterImg(chapterRequest.getChapterImg());
        Chapter updatedChapter = chapterRepository.save(chapter);
        ChapterResponse chapterResponse = new ChapterResponse(updatedChapter.getId(), updatedChapter.getChapterName(), updatedChapter.getChapterImg());
        return responseUtil.successResponse(chapterResponse, "Chapter updated successfully");
    }

    @Override
    public ResponseEntity<ResponseDTO<Void>> deleteChapter(Long id) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chapter not found with ID: " + id));
        chapterRepository.delete(chapter);
        return responseUtil.successResponse(null, "Chapter deleted successfully");
    }

}
