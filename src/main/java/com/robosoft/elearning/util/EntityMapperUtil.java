package com.robosoft.elearning.util;

import com.robosoft.elearning.dto.response.UserDetailResponse;
import com.robosoft.elearning.modal.User;
import org.springframework.stereotype.Component;

@Component
public class EntityMapperUtil {

    public UserDetailResponse convertUserToUserDetailResponse(User user) {
        if (user == null) {
            return null;
        }

        UserDetailResponse userDetailResponse = new UserDetailResponse();

        userDetailResponse.setId(user.getId());
        userDetailResponse.setEmail(user.getEmail());
        userDetailResponse.setUserName(user.getUserName());
        userDetailResponse.setProfileImageUrl(user.getProfileImageUrl());

//        userDetailResponse.setCompleterCompletedInPercentage(0.0);
//        userDetailResponse.setAverageTestScore(0.0);
//        userDetailResponse.setHighestTestScore(0.0);
//        userDetailResponse.setNotificationEnabled(true);

        return userDetailResponse;
    }
}
