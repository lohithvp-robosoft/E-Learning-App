package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.response.ChapterRecommendationResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.modal.Subject;
import com.robosoft.elearning.repository.SubjectRepository;
import com.robosoft.elearning.services.RecommendationServices;
import com.robosoft.elearning.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationServicesImpl implements RecommendationServices {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ResponseUtil responseUtil;
    @Override
    public ResponseEntity<ResponseDTO<List<ChapterRecommendationResponse>>> getChapterRecommendations() {
        List<Subject> subjects = subjectRepository.findAll();

        List<ChapterRecommendationResponse> chapterRecommendationResponses = subjects.stream()
                .flatMap(subject -> subject.getChapters().stream().limit(1))
                .map(chapter -> new ChapterRecommendationResponse(
                        chapter.getId(),
                        chapter.getSubject().getSubjectName(),
                        chapter.getChapterName(),
                        chapter.getChapterImg()
                ))
                .collect(Collectors.toList());

        return responseUtil.successResponse(chapterRecommendationResponses);
    }
}
