package com.smartgrievance.grievance_system.auth_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.EmailOtpRequest;
import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.LoginRequestDTO;
import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.SignUpRequestDTO;
import com.smartgrievance.grievance_system.auth_service.service.AuthService;
import com.smartgrievance.grievance_system.auth_service.service.ProfileSevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;

    @Autowired
    private ProfileSevice profileSevice;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequestDTO signUpRequest) {
        try{
            return authService.registerUser(signUpRequest);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO loginRequest){
        try{
            return authService.loginUser(loginRequest);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/validate/otp")
    public ResponseEntity<?> validateEmailOtp(@RequestBody EmailOtpRequest emailOtpRequest){
        // System.out.println("email : " + emailOtpRequest.getEmail() + ", otp : " + emailOtpRequest.getOtp());
        try{
            return authService.ValidateEmailOTP(emailOtpRequest);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestParam("id") Long id) {
        try{
            return profileSevice.getUserProfile(id);
        }catch(Exception e){
            return new ResponseEntity<>("Internal Server Error!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
