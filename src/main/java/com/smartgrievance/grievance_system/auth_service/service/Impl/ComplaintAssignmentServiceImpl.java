package com.smartgrievance.grievance_system.auth_service.service.Impl;


import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.ComplaintAssignmentRequestDTO;
import com.smartgrievance.grievance_system.auth_service.entity.Complaint;
import com.smartgrievance.grievance_system.auth_service.entity.ComplaintAssignment;
import com.smartgrievance.grievance_system.auth_service.entity.DepartmentOfficer;
import com.smartgrievance.grievance_system.auth_service.entity.User;
import com.smartgrievance.grievance_system.auth_service.repository.ComplaintAssignmentRepository;
import com.smartgrievance.grievance_system.auth_service.repository.ComplaintRepository;
import com.smartgrievance.grievance_system.auth_service.repository.DepartmentOfficerRepository;
import com.smartgrievance.grievance_system.auth_service.repository.UserRepository;
import com.smartgrievance.grievance_system.auth_service.service.ComplaintAssignmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ComplaintAssignmentServiceImpl implements ComplaintAssignmentService {

    @Autowired
    private ComplaintAssignmentRepository assignmentRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private DepartmentOfficerRepository officerRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Assign complaint (Admin)
    @Override
    public ResponseEntity<?> assignComplaint(ComplaintAssignmentRequestDTO dto, String adminEmail) {
        Complaint complaint = complaintRepository.findById(dto.getComplaintId())
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        DepartmentOfficer officer = officerRepository.findById(dto.getOfficerId())
                .orElseThrow(() -> new RuntimeException("Officer not found"));

        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        ComplaintAssignment assignment = new ComplaintAssignment();
        assignment.setComplaint(complaint);
        assignment.setOfficer(officer);
        assignment.setAssignedBy(admin);

        assignmentRepository.save(assignment);

        return new ResponseEntity<>("Complaint assigned successfully!", HttpStatus.CREATED);
    }

    // ✅ Get complaints assigned to an officer
    @Override
    public ResponseEntity<?> getAssignmentsByOfficer(Long officerId) {
        return new ResponseEntity<>(assignmentRepository.findByOfficerOfficerId(officerId), HttpStatus.OK);
    }

    // ✅ Get assignment history of a complaint
    @Override
    public ResponseEntity<?> getAssignmentsByComplaint(Long complaintId) {
        return new ResponseEntity<>(assignmentRepository.findByComplaintComplaintId(complaintId), HttpStatus.OK);
    }
}
