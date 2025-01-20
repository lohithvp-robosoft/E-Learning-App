package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.request.UpdateCompletedChapterRequest;
import com.robosoft.elearning.dto.response.UpdateCompletedChapterResponse;
import com.robosoft.elearning.modal.Chapter;
import com.robosoft.elearning.modal.CompletedChapter;
import com.robosoft.elearning.modal.User;
import com.robosoft.elearning.repository.ChapterRepository;
import com.robosoft.elearning.repository.CompletedChapterRepository;
import com.robosoft.elearning.repository.UserRepository;
import com.robosoft.elearning.services.CompletedChapterService;
import com.robosoft.elearning.util.EntityMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    // Get all completed chapters by user ID
    public ResponseEntity<List<UpdateCompletedChapterResponse>> getCompletedChaptersByUser(Long userId) {
        List<CompletedChapter> completedChapters = completedChapterRepository.findByUserId(userId);
        if (completedChapters.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // No completed chapters found for this user
        }

        List<UpdateCompletedChapterResponse> responses = completedChapters.stream()
                .map(entityMapperUtil::convertCompletedChapterToResponse)
                .collect(Collectors.toList());

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    // Update the completion status of a chapter
    public ResponseEntity<String> updateCompletedChapter(UpdateCompletedChapterRequest request) {
        Chapter chapter = chapterRepository.findById(request.getChapterId())
                .orElseThrow(() -> new RuntimeException("Chapter not found"));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CompletedChapter> existingEntries = completedChapterRepository.findByUserIdAndChapterId(request.getUserId(), request.getChapterId());

        CompletedChapter completedChapter;
        if (!existingEntries.isEmpty()) {
            completedChapter = existingEntries.get(0); // Update the first existing entry
        } else {
            completedChapter = new CompletedChapter();
            completedChapter.setChapter(chapter);
            completedChapter.setUser(user);
        }

        completedChapter.setCompleted(request.isCompleted());
        completedChapter.setCompletionDate(request.getCompletionDate());

        completedChapterRepository.save(completedChapter);

        return new ResponseEntity<>("Completed chapter status updated successfully!", HttpStatus.OK);
    }
}
