package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.response.CurrentlyStudyingResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserCurrentlyStudyingSubjectService {
    ResponseEntity<List<CurrentlyStudyingResponseDTO>> getCurrentlyStudyingSubjects(HttpServletRequest request);
}
