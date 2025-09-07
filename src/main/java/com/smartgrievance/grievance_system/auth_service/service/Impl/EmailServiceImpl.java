package com.smartgrievance.grievance_system.auth_service.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.smartgrievance.grievance_system.auth_service.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendVerificationEmail(String toEmail, String username, String otp) {
        String subject = "✅ Smart Grievance System - Email Verification";
        String text = "Hello " + username + " 👋,\n\n"
                + "Welcome to the **Smart Grievance System**! We're thrilled to have you on board.\n\n"
                + "🔐 **Your OTP for email verification is:** " + otp + "\n"
                + "⏳ This OTP is valid for the next 5 minutes, so please use it promptly.\n\n"
                + "If you did not request this OTP, please ignore this email.\n\n"
                + "Thank you for joining us and helping us make grievances simpler to handle!\n\n"
                + "💼 Regards,\n"
                + "Smart Grievance System Team 🚀";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }

}
