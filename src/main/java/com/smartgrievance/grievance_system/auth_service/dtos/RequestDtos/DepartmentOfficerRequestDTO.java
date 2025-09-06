package com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos;

import lombok.Data;

@Data
public class DepartmentOfficerRequestDTO {
    private Long userId;         
    private String departmentName;
    private String designation;
}
