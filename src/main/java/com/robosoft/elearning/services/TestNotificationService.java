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

    String deviceToken = "ASED";

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private UserTestProgressRepository userTestProgressRepository;

//    @Scheduled(fixedRate = 60_000) // runs every minute
//    public void checkTestTimeAndNotify() {
//        // Fetch all active tests
//        List<UserTestProgress> ongoingTests = userTestProgressRepository.findTestsByStatus(UserTestProgress.TestStatus.IN_PROGRESS);
//
//        for (UserTestProgress testProgress : ongoingTests) {
//            Test test = testProgress.getTest();
//
//            LocalDateTime testEndTime = testProgress.getTestStartTime().plusMinutes(test.getTotalTime());
//            long minutesLeft = ChronoUnit.MINUTES.between(LocalDateTime.now(), testEndTime);
//
//            if (minutesLeft == 5) { // Notify 5 minutes before the test ends
//                try {
//                    firebaseService.sendPushNotification(deviceToken,
//                            "Test Ending Soon!",
//                            "Hurry up! You have few minutes left to complete the test.");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}

