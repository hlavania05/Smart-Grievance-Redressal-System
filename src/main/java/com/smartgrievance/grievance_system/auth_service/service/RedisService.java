package com.smartgrievance.grievance_system.auth_service.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.SignUpRequestDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final long OTP_EXPIRY = 300;
    private final RedisTemplate<String, Object> redisTemplate;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    

    public void saveUserWithOtp(String email, SignUpRequestDTO dto, String otp) {
        validateEmail(email);
        if (dto == null) throw new IllegalArgumentException("SignUpRequestDTO cannot be null");
        if (otp == null) throw new IllegalArgumentException("OTP cannot be null");

        Map<String, Object> data = new HashMap<>();
        data.put("user", dto);
        data.put("otp", otp);

        redisTemplate.opsForHash().putAll(email, data);
        redisTemplate.expire(email, OTP_EXPIRY, TimeUnit.SECONDS);
    }

    public String getOtp(String email) {
        validateEmail(email);
        Object otp = redisTemplate.opsForHash().get(email, "otp");
        return otp != null ? otp.toString() : null;
    }

    public SignUpRequestDTO getUser(String email) {
        validateEmail(email);
        Object user = redisTemplate.opsForHash().get(email, "user");
        return user != null ? (SignUpRequestDTO) user : null;
    }

    public void delete(String email) {
        validateEmail(email);
        redisTemplate.delete(email);
    }

    // Helper method to validate email / key
    private void validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Redis key (email) cannot be null or empty");
        }
    }
}
