package com.smartgrievance.grievance_system.auth_service.service;

import org.springframework.http.ResponseEntity;

import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.EmailOtpRequest;
import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.LoginRequestDTO;
import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.SignUpRequestDTO;

public interface AuthService {
    ResponseEntity<?> registerUser(SignUpRequestDTO signUpRequest);
    ResponseEntity<?> loginUser(LoginRequestDTO loginRequest);
    ResponseEntity<?> ValidateEmailOTP(EmailOtpRequest emailOtpRequest);
}
