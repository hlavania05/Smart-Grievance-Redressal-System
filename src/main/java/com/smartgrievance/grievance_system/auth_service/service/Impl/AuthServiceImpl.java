package com.smartgrievance.grievance_system.auth_service.service.Impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.EmailOtpRequest;
import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.LoginRequestDTO;
import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.SignUpRequestDTO;
import com.smartgrievance.grievance_system.auth_service.entity.Role;
import com.smartgrievance.grievance_system.auth_service.entity.User;
import com.smartgrievance.grievance_system.auth_service.repository.RoleRepository;
import com.smartgrievance.grievance_system.auth_service.repository.UserRepository;
import com.smartgrievance.grievance_system.auth_service.security.JwtUtil;
import com.smartgrievance.grievance_system.auth_service.service.AuthService;
import com.smartgrievance.grievance_system.auth_service.service.EmailService;
import com.smartgrievance.grievance_system.auth_service.service.RedisService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisService redisService;

    @Autowired
    private EmailService emailService;

    public ResponseEntity<?> registerUser(SignUpRequestDTO signUpRequest) {
        if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("StatusCode", HttpStatus.CONFLICT.value());
            response.put("Message : ", "Email is Already in use");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        String otp = String.valueOf(new Random().nextInt(9999));

        redisService.saveUserWithOtp(signUpRequest.getEmail(), signUpRequest, otp);

        emailService.sendVerificationEmail(signUpRequest.getEmail(), signUpRequest.getFullName(), otp);

        Map<String, Object> response = new HashMap<>();
        response.put("StatusCode : ", HttpStatus.OK.value());
        response.put("Message : ", "OTP sent to your email. Please verify.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<?> loginUser(LoginRequestDTO loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElse(null);
        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            Map<String, Object> response = new HashMap<>();
            response.put("StatusCode : ", HttpStatus.UNAUTHORIZED.value());
            response.put("Message : ", "Invalid Email or Password!");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        String roleName = user.getRole().getRoleName();
        String token = jwtUtil.generateToken(user.getEmail(), roleName);

        Map<String, Object> response = new HashMap<>();
        response.put("StatusCode : ", HttpStatus.OK.value());
        response.put("Message : ", "Login Succesfull!!");
        response.put("JwtToken : ", token);
        response.put("Role : ", user.getRole().getRoleName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public String hashedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public ResponseEntity<?> ValidateEmailOTP(EmailOtpRequest emailOtpRequest) {
        String email = emailOtpRequest.getEmail();
        String otp = emailOtpRequest.getOtp();

        String storedOtp = redisService.getOtp(email);

        if(storedOtp == null || !storedOtp.equals(otp)){
            Map<String, Object> response = new HashMap<>();
            response.put("StatusCode : ", HttpStatus.BAD_REQUEST.value());
            response.put("Message : ", "Invalid or expired OTP!!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        SignUpRequestDTO userDto = redisService.getUser(email);
        if(userDto == null){
            Map<String, Object> response = new HashMap<>();
            response.put("StatusCode : ", HttpStatus.BAD_REQUEST.value());
            response.put("Message : ", "UserDetails Expired!!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Role defaultRole = roleRepository.findByRoleName("CITIZEN")
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAddress(userDto.getAddress());
        user.setRole(defaultRole);

        userRepository.save(user);
        redisService.delete(email);

        return new ResponseEntity<>("OTP Verified, User Registered Succesfully!", HttpStatus.CREATED);
    }
}
