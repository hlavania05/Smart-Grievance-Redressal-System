package com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos;

import lombok.Data;

@Data
public class ComplaintRequestDTO {
    private String title;
    private String description;
    private String location;
    private String attachmentUrl;  
    private Long categoryId;      
}
