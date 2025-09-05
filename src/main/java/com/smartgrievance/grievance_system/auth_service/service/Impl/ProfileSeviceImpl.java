package com.smartgrievance.grievance_system.auth_service.service.Impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.smartgrievance.grievance_system.auth_service.entity.User;
import com.smartgrievance.grievance_system.auth_service.repository.UserRepository;
import com.smartgrievance.grievance_system.auth_service.service.ProfileSevice;

@Service
public class ProfileSeviceImpl implements ProfileSevice {
    
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> getUserProfile(Long userId){
        User user = userRepository.findByUserId(userId).orElse(null);

        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("fullName", user.getFullName());
        response.put("email", user.getEmail());
        response.put("phoneNumber", user.getPhoneNumber());
        response.put("address", user.getAddress());
        response.put("role", user.getRole().getRoleName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
