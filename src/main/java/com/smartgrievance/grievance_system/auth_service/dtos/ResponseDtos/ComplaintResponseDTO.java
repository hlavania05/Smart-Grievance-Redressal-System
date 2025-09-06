package com.smartgrievance.grievance_system.auth_service.dtos.ResponseDtos;

import lombok.Data;

@Data
public class ComplaintResponseDTO {
    private Long complaintId;
    private String title;
    private String description;
    private String location;
    private String attachmentUrl;
    private String status;
    private String category;
    private String createdBy;
    private String createdAt;
}
