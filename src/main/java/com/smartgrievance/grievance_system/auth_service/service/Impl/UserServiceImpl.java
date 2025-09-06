package com.smartgrievance.grievance_system.auth_service.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.CreateUserRequestDTO;
import com.smartgrievance.grievance_system.auth_service.dtos.ResponseDtos.UserResponseDTO;
import com.smartgrievance.grievance_system.auth_service.entity.Role;
import com.smartgrievance.grievance_system.auth_service.entity.User;
import com.smartgrievance.grievance_system.auth_service.repository.RoleRepository;
import com.smartgrievance.grievance_system.auth_service.repository.UserRepository;
import com.smartgrievance.grievance_system.auth_service.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDTO> response = users.stream().map(user -> {
            UserResponseDTO userResponse = new UserResponseDTO();
            userResponse.setName(user.getFullName());
            userResponse.setEmail(user.getEmail());
            userResponse.setPhoneNo(user.getPhoneNumber());
            userResponse.setAddress(user.getAddress());
            userResponse.setUserId(user.getUserId());
            userResponse.setRoleName(user.getRole().getRoleName());
            return userResponse;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<?> createUser(CreateUserRequestDTO createUserRequestDTO) {
        User user = userRepository.findByEmail(createUserRequestDTO.getEmail()).orElse(null);
        if (user != null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("StatusCode : ", HttpStatus.CONFLICT.value());
            errorResponse.put("Message : ", "Email Already Exists!!");
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        }

        String roleName = createUserRequestDTO.getRoleName();
        if (roleName == null) {
            throw new IllegalArgumentException("Role name is required!");
        }
        Role role = roleRepository.findByRoleName(roleName.toUpperCase()).orElse(null);

        if (role == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("StatusCode : ", HttpStatus.BAD_REQUEST.value());
            errorResponse.put("Message : ", "Role Not Found");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        User newUser = new User();
        newUser.setFullName(createUserRequestDTO.getName());
        newUser.setEmail(createUserRequestDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(createUserRequestDTO.getPassword()));
        newUser.setPhoneNumber(createUserRequestDTO.getPhoneNo());
        newUser.setAddress(createUserRequestDTO.getAddress());
        newUser.setRole(role);

        return new ResponseEntity<>("User Created!!", HttpStatus.CREATED);
    }

    public ResponseEntity<?> deleteUser(Long id){
        User user = userRepository.findByUserId(id).orElse(null);
        if(user == null){
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("StatusCode : ", HttpStatus.BAD_REQUEST.value());
            errorResponse.put("Message : ", "User does not Existed!!");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        userRepository.deleteById(id);
        return new ResponseEntity<>("User Deleted!!", HttpStatus.OK);
    }
}
