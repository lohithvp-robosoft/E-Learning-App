package com.robosoft.elearning.services;

import com.robosoft.elearning.repository.UserTestProgressRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TestNotificationService {

    String deviceToken = "NULL"; //WAITING TO GET DEVICE TOKEN

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private UserTestProgressRepository userTestProgressRepository;

}

