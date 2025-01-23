package com.robosoft.elearning.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FirebaseService {

    @Value("${firebase.config.path}")
    private String firebaseConfigPath;

    @Autowired
    private NotificationServices notificationService;

    public void initializeFirebase() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(firebaseConfigPath);
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);
    }

    public void sendPushNotification(String deviceToken, String title, String body, Long userId) throws Exception {

        notificationService.saveNotification(title, body, userId);
        Message message = Message.builder()
                .setToken(deviceToken)
                .putData("title", title)
                .putData("body", body)
                .build();

        FirebaseMessaging.getInstance().send(message);
    }
}
