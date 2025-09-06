package com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos;

import lombok.Data;

@Data
public class CreateUserRequestDTO {
    private String name;
    private String email;
    private String phoneNo;
    private String address;
    private String password;
}
