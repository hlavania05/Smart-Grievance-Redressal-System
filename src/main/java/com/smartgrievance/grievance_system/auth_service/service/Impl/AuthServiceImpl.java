package com.smartgrievance.grievance_system.auth_service.service.Impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.LoginRequestDTO;
import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.SignUpRequestDTO;
import com.smartgrievance.grievance_system.auth_service.entity.Role;
import com.smartgrievance.grievance_system.auth_service.entity.User;
import com.smartgrievance.grievance_system.auth_service.repository.RoleRepository;
import com.smartgrievance.grievance_system.auth_service.repository.UserRepository;
import com.smartgrievance.grievance_system.auth_service.security.JwtUtil;
import com.smartgrievance.grievance_system.auth_service.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private JwtUtil jwtUtil;
    
    public ResponseEntity<?> registerUser(SignUpRequestDTO signUpRequest){
        if(userRepository.findByEmail(signUpRequest.getEmail()).isPresent()){
            Map<String, Object> response = new HashMap<>();
            response.put("StatusCode", HttpStatus.CONFLICT.value());
            response.put("Message : ", "Email is Already in use");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        Role CitizenRole = roleRepository.findByRoleName("CITIZEN").orElseThrow(() -> new RuntimeException("Default role not found"));

        User user = new User();
        user.setFullName(signUpRequest.getFullName());
        user.setEmail(signUpRequest.getEmail());
        user.setAddress(signUpRequest.getEmail());
        user.setPassword(hashedPassword(signUpRequest.getPassword()));
        user.setPhoneNumber(signUpRequest.getPhoneNumber());
        user.setRole(CitizenRole);

        userRepository.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("StatusCode : ", HttpStatus.CREATED.value());
        response.put("Message : ", "User Successfully Create");
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    public ResponseEntity<?> loginUser(LoginRequestDTO loginRequest){
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElse(null);
        if(user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            Map<String, Object> response = new HashMap<>();
            response.put("StatusCode : ", HttpStatus.UNAUTHORIZED.value());
            response.put("Message : ", "Invalid Email or Password!");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        // Generate JWT token
        String roleName = user.getRole().getRoleName();
        String token = jwtUtil.generateToken(user.getEmail(), roleName);

        Map<String, Object> response = new HashMap<>();
        response.put("StatusCode : ", HttpStatus.OK.value());
        response.put("Message : ", "Login Succesfull!!");
        response.put("JwtToken : ", token);
        response.put("Role : ", user.getRole().getRoleName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public String hashedPassword(String password){
        return passwordEncoder.encode(password);
    }
}
