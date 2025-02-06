package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.response.ChapterRecommendationResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecommendationServices {

    ResponseEntity<ResponseDTO<List<ChapterRecommendationResponse>>> getChapterRecommendations();
}
