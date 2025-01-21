package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.request.UpdateCompletedChapterRequest;
import com.robosoft.elearning.dto.response.CompletedChapterResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.modal.Chapter;
import com.robosoft.elearning.modal.CompletedChapter;
import com.robosoft.elearning.modal.User;
import com.robosoft.elearning.repository.ChapterRepository;
import com.robosoft.elearning.repository.CompletedChapterRepository;
import com.robosoft.elearning.repository.UserRepository;
import com.robosoft.elearning.services.CompletedChapterService;
import com.robosoft.elearning.util.EntityMapperUtil;
import com.robosoft.elearning.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CompletedChapterServiceImpl implements CompletedChapterService {
    @Autowired
    private CompletedChapterRepository completedChapterRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityMapperUtil entityMapperUtil;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ResponseUtil responseUtil;

    @Override
    public ResponseEntity<ResponseDTO<List<CompletedChapterResponse>>> getCompletedChaptersByUser(HttpServletRequest request) {
        Long userId = jwtUtils.getUserIdFromRequestHeader(request);

        List<CompletedChapter> completedChapters = completedChapterRepository.findByUserId(userId);
        if (completedChapters.isEmpty()) {
            return responseUtil.errorResponse("No completed chapters found for the user.");
        }

        List<CompletedChapterResponse> responses = completedChapters.stream()
                .map(entityMapperUtil::convertCompletedChapterToResponse)
                .collect(Collectors.toList());

        return responseUtil.successResponse(responses);
    }

    @Override
    public ResponseEntity<ResponseDTO<String>> updateCompletedChapter(HttpServletRequest request, UpdateCompletedChapterRequest updateRequest) {
        Long userId = jwtUtils.getUserIdFromRequestHeader(request);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Chapter chapter = chapterRepository.findById(updateRequest.getChapterId())
                .orElseThrow(() -> new NotFoundException("Chapter not found"));

        CompletedChapter completedChapter = completedChapterRepository.findByUserAndChapter(user, chapter)
                .orElseGet(() -> new CompletedChapter(user, chapter));

        completedChapter.setCompleted(updateRequest.isCompleted());
        completedChapter.setCompletionDate(updateRequest.getCompletionDate());
        completedChapterRepository.save(completedChapter);

        return responseUtil.successResponse("Completed chapter status updated successfully!");
    }
}