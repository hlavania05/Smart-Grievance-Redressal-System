package com.smartgrievance.grievance_system.auth_service.service;

import org.springframework.http.ResponseEntity;

public interface ProfileSevice {
    ResponseEntity<?> getUserProfile(Long id);
}
