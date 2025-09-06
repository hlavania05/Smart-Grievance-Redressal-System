package com.smartgrievance.grievance_system.auth_service.service;

import org.springframework.http.ResponseEntity;

import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.CreateUserRequestDTO;

public interface UserService {
    ResponseEntity<?> getAllUsers();
    ResponseEntity<?> createUser(CreateUserRequestDTO createUserRequest);
    ResponseEntity<?> deleteUser(Long id);
}
