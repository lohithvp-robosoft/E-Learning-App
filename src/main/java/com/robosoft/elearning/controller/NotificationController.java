package com.robosoft.elearning.controller;

import com.robosoft.elearning.dto.response.NotificationResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.services.NotificationServices;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @Autowired
    private NotificationServices notificationService;

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDTO<List<NotificationResponse>>> getNotifications(@PathVariable Long userId) {
        return notificationService.getNotifications(userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseDTO<Void>> clearNotifications(@PathVariable Long userId) {
        return notificationService.clearNotifications(userId);

    }

    @PutMapping("/toggle")
    public ResponseEntity<ResponseDTO<Void>> toggleNotification(HttpServletRequest request){
        return notificationService.toggleNotification(request);
    }
}
