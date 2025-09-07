package com.smartgrievance.grievance_system.auth_service.service;

public interface EmailService {
    void sendVerificationEmail(String toEmail, String username, String otp);
} 
