package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.response.ChapterResponse;
import com.robosoft.elearning.modal.Chapter;
import com.robosoft.elearning.repository.ChapterRepository;
import com.robosoft.elearning.services.ChapterService;
import com.robosoft.elearning.util.EntityMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private EntityMapperUtil entityMapperUtil;

    @Override
    public ResponseEntity<ChapterResponse> getChapterById(long id) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chapter not found"));
        ChapterResponse chapterResponse = entityMapperUtil.convertChapterToChapterResponse(chapter);
        return ResponseEntity.ok(chapterResponse);
    }

    @Override
    public ResponseEntity<ChapterResponse> getChapterWithLessons(long chapterId) {
        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new RuntimeException("Chapter not found"));

        ChapterResponse chapterResponse = entityMapperUtil.convertChapterToChapterResponse(chapter);
        return ResponseEntity.ok(chapterResponse);
    }
}
