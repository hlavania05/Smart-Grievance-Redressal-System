package com.smartgrievance.grievance_system.auth_service.service.Impl;

import com.smartgrievance.grievance_system.auth_service.controller.ComplaintStatusController;
import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.ComplaintAssignmentRequestDTO;
import com.smartgrievance.grievance_system.auth_service.dtos.ResponseDtos.AssignmentDetailsDTO;
import com.smartgrievance.grievance_system.auth_service.entity.Complaint;
import com.smartgrievance.grievance_system.auth_service.entity.ComplaintAssignment;
import com.smartgrievance.grievance_system.auth_service.entity.DepartmentOfficer;
import com.smartgrievance.grievance_system.auth_service.entity.User;
import com.smartgrievance.grievance_system.auth_service.repository.ComplaintAssignmentRepository;
import com.smartgrievance.grievance_system.auth_service.repository.ComplaintRepository;
import com.smartgrievance.grievance_system.auth_service.repository.ComplaintStatusRepository;
import com.smartgrievance.grievance_system.auth_service.repository.DepartmentOfficerRepository;
import com.smartgrievance.grievance_system.auth_service.repository.UserRepository;
import com.smartgrievance.grievance_system.auth_service.service.ComplaintAssignmentService;

import java.time.LocalDateTime;
import java.util.List;

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

    @Autowired
    private ComplaintStatusRepository complaintStatusRepository;

    @Override
    public ResponseEntity<?> assignComplaint(ComplaintAssignmentRequestDTO dto, String adminEmail) {
        Complaint complaint = complaintRepository.findById(dto.getComplaintId())
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        if (!"NOT_ASSIGNED".equals(complaint.getStatus().getStatusName())) {
            return new ResponseEntity<>("Complaint is already assigned!", HttpStatus.BAD_REQUEST);
        }

        DepartmentOfficer officer = officerRepository.findById(dto.getOfficerId())
                .orElseThrow(() -> new RuntimeException("Officer not found"));

        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        ComplaintAssignment assignment = new ComplaintAssignment();
        assignment.setComplaint(complaint);
        assignment.setOfficer(officer);
        assignment.setAssignedBy(admin);
        assignment.setAssignedAt(LocalDateTime.now());

        assignmentRepository.save(assignment);

        complaint.setStatus(complaintStatusRepository.findByStatusName("ASSIGNED").orElse(null));
        complaintRepository.save(complaint);

        return new ResponseEntity<>("Complaint assigned successfully!", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getAssignmentsByOfficer(Long officerId) {
        List<ComplaintAssignment> assignments = assignmentRepository.findByOfficerOfficerId(officerId);

        if (assignments.isEmpty()) {
            return new ResponseEntity<>("No assignments found for this officer", HttpStatus.NOT_FOUND);
        }

        List<AssignmentDetailsDTO> assignmentDTOs = assignments.stream().map(a -> {
            AssignmentDetailsDTO dto = new AssignmentDetailsDTO();
            dto.setAssignmentId(a.getAssignmentId());

            dto.setComplaintId(a.getComplaint().getComplaintId());
            dto.setComplaintTitle(a.getComplaint().getTitle());
            dto.setComplaintDescription(a.getComplaint().getDescription());
            dto.setComplaintStatus(a.getComplaint().getStatus().getStatusName());

            dto.setOfficerId(a.getOfficer().getOfficerId());
            dto.setOfficerName(a.getOfficer().getUser().getFullName());

            dto.setAssignedById(a.getAssignedBy().getUserId());
            dto.setAssignedByName(a.getAssignedBy().getFullName());

            dto.setAssignedAt(a.getAssignedAt());

            return dto;
        }).toList();

        return new ResponseEntity<>(assignmentDTOs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAssignmentsByComplaint(Long complaintId) {
        List<ComplaintAssignment> assignments = assignmentRepository.findByComplaintComplaintId(complaintId);

        if (assignments.isEmpty()) {
            return new ResponseEntity<>("No assignment history found for this complaint", HttpStatus.NOT_FOUND);
        }

        List<AssignmentDetailsDTO> assignmentDTOs = assignments.stream().map(a -> {
            AssignmentDetailsDTO dto = new AssignmentDetailsDTO();
            dto.setAssignmentId(a.getAssignmentId());

            dto.setComplaintId(a.getComplaint().getComplaintId());
            dto.setComplaintTitle(a.getComplaint().getTitle());
            dto.setComplaintDescription(a.getComplaint().getDescription());
            dto.setComplaintStatus(a.getComplaint().getStatus().getStatusName());

            dto.setOfficerId(a.getOfficer().getOfficerId());
            dto.setOfficerName(a.getOfficer().getUser().getFullName());

            dto.setAssignedById(a.getAssignedBy().getUserId());
            dto.setAssignedByName(a.getAssignedBy().getFullName());

            dto.setAssignedAt(a.getAssignedAt());

            return dto;
        }).toList();

        return new ResponseEntity<>(assignmentDTOs, HttpStatus.OK);
    }

}
