package com.smartgrievance.grievance_system.auth_service.dtos.ResponseDtos;


import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentDetailsDTO {
    private Long assignmentId;

    // Complaint details
    private Long complaintId;
    private String complaintTitle;
    private String complaintDescription;
    private String complaintStatus;

    // Officer details
    private Long officerId;
    private String officerName;

    // Admin who assigned
    private Long assignedById;
    private String assignedByName;

    // Assignment timestamp
    private LocalDateTime assignedAt;
}

