package com.smartgrievance.grievance_system.auth_service.dtos.ResponseDtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintDTO {
    private Long complaintId;
    private String title;
    private String description;
    private String location;
    private String attachmentUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String userName;  
    private String categoryName;
    private String statusName;
}

