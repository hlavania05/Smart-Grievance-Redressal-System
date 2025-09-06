package com.smartgrievance.grievance_system.auth_service.service;

import org.springframework.http.ResponseEntity;
import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.ComplaintRequestDTO;
import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.UpdateComplaintStatusRequestDTO;

public interface ComplaintService {
    ResponseEntity<?> fileComplaint(ComplaintRequestDTO dto, String userEmail);
    ResponseEntity<?> getMyComplaints(String userEmail);
    ResponseEntity<?> getComplaintById(Long id);
    ResponseEntity<?> getAllComplaints();
    ResponseEntity<?> updateComplaint(Long id, ComplaintRequestDTO dto, String userEmail);
    ResponseEntity<?> deleteComplaint(Long id);
    ResponseEntity<?> updateComplaintStatus(Long id, UpdateComplaintStatusRequestDTO updateComplaintStatusRequest);
}
