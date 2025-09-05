package com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String password;
}
