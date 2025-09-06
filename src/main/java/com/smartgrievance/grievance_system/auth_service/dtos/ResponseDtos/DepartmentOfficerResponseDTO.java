package com.smartgrievance.grievance_system.auth_service.dtos.ResponseDtos;

import lombok.Data;

@Data
public class DepartmentOfficerResponseDTO {
    private Long officerId;
    private Long userId;
    private String fullName;
    private String email;
    private String departmentName;
    private String designation;
}
