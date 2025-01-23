package com.robosoft.elearning.services;

import com.robosoft.elearning.dto.response.NotificationResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.modal.Notification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationServices {

    void saveNotification(String title, String message, Long userId);

    ResponseEntity<ResponseDTO<List<NotificationResponse>>> getNotifications(Long userId);

    ResponseEntity<ResponseDTO<Void>> clearNotifications(Long userId);

}
