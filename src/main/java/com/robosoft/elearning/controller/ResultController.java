package com.robosoft.elearning.controller;

import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.dto.response.UserTestScoreResponse;
import com.robosoft.elearning.services.ResultServices;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    @Autowired
    private ResultServices resultServices;

    @GetMapping()
    public ResponseEntity<ResponseDTO<List<UserTestScoreResponse>>> getResultsByUser(HttpServletRequest request)  {
        return resultServices.getAllScoreOfAUser(request);
    }

    @GetMapping("/subject/{subjectId}")
    public Object getResultsBySubject(@PathVariable Long subjectId, HttpServletRequest request) {
        return resultServices.getAllScoreOfAUserBySubjectId(subjectId, request);
    }
}
