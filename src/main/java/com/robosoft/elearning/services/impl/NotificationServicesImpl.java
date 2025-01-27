package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.response.NotificationResponse;
import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.exception.NotFoundException;
import com.robosoft.elearning.jwt.JwtUtils;
import com.robosoft.elearning.modal.Notification;
import com.robosoft.elearning.modal.User;
import com.robosoft.elearning.repository.NotificationRepository;
import com.robosoft.elearning.repository.UserRepository;
import com.robosoft.elearning.services.NotificationServices;
import com.robosoft.elearning.util.EntityMapperUtil;
import com.robosoft.elearning.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${message.error.userNotFound}")
    private String userNotFound;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void saveNotification(String title, String message, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userNotFound));
        Notification notification = new Notification(title, message, user);
        notificationRepository.save(notification);
    }

    @Override
    public ResponseEntity<ResponseDTO<List<NotificationResponse>>> getNotifications(HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);
        Long userId = user.getId();
        List<Notification> notificationList = notificationRepository.findByUserIdOrderByTimestampDesc(userId);
        if(notificationList.isEmpty()){
            throw new NotFoundException("No Notification Found");
        }
        List<NotificationResponse> notificationResponseList = notificationList.stream()
                .map(notification -> mapperUtil.toNotificationResponse(notification))
                .collect(Collectors.toList());
        return responseUtil.successResponse(notificationResponseList);
    }

    @Override
    public ResponseEntity<ResponseDTO<Void>> clearNotifications(HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);
        Long userId = user.getId();
        notificationRepository.deleteByUserId(userId);
        return responseUtil.successResponse(null);
    }

    @Override
    public ResponseEntity<ResponseDTO<Void>> toggleNotification(HttpServletRequest request) {
        User user = jwtUtils.getUserDataFromRequest(request);

        if (user == null) {
            return responseUtil.errorResponse("User Not Found");
        }

        user.setNotificationEnabled(!user.isNotificationEnabled());

        userRepository.save(user);

        return responseUtil.successResponse(null);
    }


}
