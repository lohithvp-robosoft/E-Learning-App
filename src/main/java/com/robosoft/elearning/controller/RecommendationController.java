package com.robosoft.elearning.controller;

import com.robosoft.elearning.dto.response.ChapterRecommendationResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.services.RecommendationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationServices recommendationService;

    @GetMapping("/chapters")
    public ResponseEntity<ResponseDTO<List<ChapterRecommendationResponse>>> getChapterRecommendations() {
        return recommendationService.getChapterRecommendations();
    }

}
