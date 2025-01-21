package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.request.LikeLessonRequestDTO;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.modal.Lesson;
import com.robosoft.elearning.modal.User;
import com.robosoft.elearning.modal.UserLiked;
import com.robosoft.elearning.repository.LessonRepository;
import com.robosoft.elearning.repository.UserLikedRepository;
import com.robosoft.elearning.repository.UserRepository;
import com.robosoft.elearning.services.UserLikedService;
import com.robosoft.elearning.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserLikedServiceImpl implements UserLikedService {
    @Autowired
    private UserLikedRepository userLikedRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResponseUtil responseUtil;

    @Override
    public ResponseEntity<ResponseDTO<String>> likeLesson(LikeLessonRequestDTO requestDTO, HttpServletRequest request) {
        try {
            Long userId = jwtUtils.getUserIdFromRequestHeader(request);

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Lesson lesson = lessonRepository.findById(requestDTO.getLessonId())
                    .orElseThrow(() -> new RuntimeException("Lesson not found"));

            UserLiked userLiked = userLikedRepository.findByUserIdAndLessonId(userId, requestDTO.getLessonId())
                    .orElseGet(() -> {
                        UserLiked newLike = new UserLiked();
                        newLike.setUser(user);
                        newLike.setLesson(lesson);
                        return newLike;
                    });

            userLiked.setLiked(true);
            userLikedRepository.save(userLiked);

            return responseUtil.successResponse("Lesson liked successfully");
        } catch (Exception e) {
            return responseUtil.errorResponse(e.getMessage());
        }
    }
}
