package com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos;

import lombok.Data;

@Data
public class ComplaintAssignmentRequestDTO {
    private Long complaintId;
    private Long officerId;
}
