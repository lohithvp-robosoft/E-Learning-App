package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.response.NotificationResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.modal.Notification;
import com.robosoft.elearning.modal.User;
import com.robosoft.elearning.repository.NotificationRepository;
import com.robosoft.elearning.repository.UserRepository;
import com.robosoft.elearning.services.NotificationServices;
import com.robosoft.elearning.util.EntityMapperUtil;
import com.robosoft.elearning.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServicesImpl implements NotificationServices {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityMapperUtil mapperUtil;

    @Autowired
    private ResponseUtil responseUtil;

    @Override
    public void saveNotification(String title, String message, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Notification notification = new Notification(title, message, user);
        notificationRepository.save(notification);
    }

    @Override
    public ResponseEntity<ResponseDTO<List<NotificationResponse>>> getNotifications(Long userId) {
        List<Notification> notificationList = notificationRepository.findByUserIdOrderByTimestampDesc(userId);
        List<NotificationResponse> notificationResponseList = notificationList.stream()
                .map(notification -> mapperUtil.toNotificationResponse(notification))
                .collect(Collectors.toList());
        return responseUtil.successResponse(notificationResponseList);
    }

    @Override
    public ResponseEntity<ResponseDTO<Void>> clearNotifications(Long userId) {
        notificationRepository.deleteByUserId(userId);
        return responseUtil.successResponse(null);
    }
}
