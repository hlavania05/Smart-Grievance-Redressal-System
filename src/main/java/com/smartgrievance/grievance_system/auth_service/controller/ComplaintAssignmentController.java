package com.smartgrievance.grievance_system.auth_service.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.ComplaintAssignmentRequestDTO;
import com.smartgrievance.grievance_system.auth_service.security.SecurityUtils;
import com.smartgrievance.grievance_system.auth_service.service.ComplaintAssignmentService;

@RestController
@RequestMapping("/api/assignments")
public class ComplaintAssignmentController {

    @Autowired
    private ComplaintAssignmentService assignmentService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> assignComplaint(@RequestBody ComplaintAssignmentRequestDTO dto) {
        String adminEmail = SecurityUtils.getCurrentUserEmail();
        return assignmentService.assignComplaint(dto, adminEmail);
    }

    @GetMapping("/officer/{officerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OFFICER')")
    public ResponseEntity<?> getAssignmentsByOfficer(@PathVariable Long officerId) {
        return assignmentService.getAssignmentsByOfficer(officerId);
    }

    @GetMapping("/complaint/{complaintId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OFFICER')")
    public ResponseEntity<?> getAssignmentsByComplaint(@PathVariable Long complaintId) {
        return assignmentService.getAssignmentsByComplaint(complaintId);
    }
}
