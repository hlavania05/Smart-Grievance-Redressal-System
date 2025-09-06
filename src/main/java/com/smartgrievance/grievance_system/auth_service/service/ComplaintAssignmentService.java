package com.smartgrievance.grievance_system.auth_service.service;

import org.springframework.http.ResponseEntity;

import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.ComplaintAssignmentRequestDTO;

public interface ComplaintAssignmentService {
    ResponseEntity<?> assignComplaint(ComplaintAssignmentRequestDTO dto, String adminEmail);
    ResponseEntity<?> getAssignmentsByOfficer(Long officerId);
    ResponseEntity<?> getAssignmentsByComplaint(Long complaintId);
}
