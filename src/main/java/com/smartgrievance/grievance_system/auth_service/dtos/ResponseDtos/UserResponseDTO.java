package com.smartgrievance.grievance_system.auth_service.dtos.ResponseDtos;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long userId;
    private String name;
    private String email;
    private String phoneNo;
    private String address;
    private String roleName;
}
