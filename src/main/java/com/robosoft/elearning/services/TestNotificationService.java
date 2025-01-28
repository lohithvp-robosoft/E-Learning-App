package com.robosoft.elearning.services;

import com.robosoft.elearning.modal.Test;
import com.robosoft.elearning.modal.UserTestProgress;
import com.robosoft.elearning.repository.UserTestProgressRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TestNotificationService {

    String deviceToken = "NULL"; //WAITING TO GET DEVICE TOKEN

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private UserTestProgressRepository userTestProgressRepository;

}

