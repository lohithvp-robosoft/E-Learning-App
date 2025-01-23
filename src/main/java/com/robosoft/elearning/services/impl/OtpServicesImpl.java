package com.robosoft.elearning.services.impl;

import com.robosoft.elearning.dto.response.ResponseDTO;
import com.robosoft.elearning.services.OtpServices;
import com.robosoft.elearning.util.EmailHandler;
import com.robosoft.elearning.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OtpServicesImpl implements OtpServices {
    private static final long OTP_EXPIRATION_TIME = 5 * 60 * 1000;

    @Autowired
    private EmailHandler emailHandler;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ResponseUtil responseUtil;

    @Override
    public ResponseEntity<ResponseDTO<Void>> sendOtp(String email, String subject, String content) {
        String otp = generateOtp();
        emailHandler.sendMail(email, subject, content + " " + otp);
        storeOtpInRedis(email, otp);
        return responseUtil.successResponse(null, "Successfully sent the email to " + email);
    }

    @Override
    public String generateOtp() {
        Random random = new Random();
        return String.valueOf(random.nextInt(9000) + 1000);
    }

    @Override
    public void storeOtpInRedis(String email, String otp) {
        redisTemplate.opsForValue().set(email, otp, OTP_EXPIRATION_TIME, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean validateOtp(String email, String otp) {
        String storedOtp = redisTemplate.opsForValue().get(email);
        return storedOtp != null && storedOtp.equals(otp);
    }
}
