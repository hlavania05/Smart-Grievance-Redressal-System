package com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos;

import lombok.Data;

@Data
public class SignUpRequestDTO {
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
}
