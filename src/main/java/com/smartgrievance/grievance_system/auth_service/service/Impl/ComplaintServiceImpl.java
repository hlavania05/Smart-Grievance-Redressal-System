package com.smartgrievance.grievance_system.auth_service.service.Impl;

import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.ComplaintRequestDTO;
import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.UpdateComplaintStatusRequestDTO;
import com.smartgrievance.grievance_system.auth_service.entity.Complaint;
import com.smartgrievance.grievance_system.auth_service.entity.ComplaintCategory;
import com.smartgrievance.grievance_system.auth_service.entity.ComplaintStatus;
import com.smartgrievance.grievance_system.auth_service.entity.User;
import com.smartgrievance.grievance_system.auth_service.repository.ComplaintCategoryRepository;
import com.smartgrievance.grievance_system.auth_service.repository.ComplaintRepository;
import com.smartgrievance.grievance_system.auth_service.repository.ComplaintStatusRepository;
import com.smartgrievance.grievance_system.auth_service.repository.UserRepository;
import com.smartgrievance.grievance_system.auth_service.service.ComplaintService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private ComplaintCategoryRepository categoryRepository;

    @Autowired
    private ComplaintStatusRepository statusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComplaintStatusRepository complaintStatusRepository;

    @Override
    public ResponseEntity<?> fileComplaint(ComplaintRequestDTO dto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ComplaintCategory category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        ComplaintStatus defaultStatus = statusRepository.findByStatusName("PENDING")
                .orElseThrow(() -> new RuntimeException("Default status not found"));

        Complaint complaint = new Complaint();
        complaint.setTitle(dto.getTitle());
        complaint.setDescription(dto.getDescription());
        complaint.setLocation(dto.getLocation());
        complaint.setAttachmentUrl(dto.getAttachmentUrl());
        complaint.setUser(user);
        complaint.setCategory(category);
        complaint.setStatus(defaultStatus);
        complaint.setCreatedAt(LocalDateTime.now());
        complaint.setUpdatedAt(LocalDateTime.now());

        complaintRepository.save(complaint);

        return new ResponseEntity<>("Complaint filed successfully!", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getMyComplaints(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Complaint> complaints = complaintRepository.findByUser(user);
        return new ResponseEntity<>(complaints, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getComplaintById(Long id) {
        Optional<Complaint> complaint = complaintRepository.findById(id);
        if (complaint.isEmpty()) {
            return new ResponseEntity<>("Complaint not found!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(complaint.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllComplaints() {
        return new ResponseEntity<>(complaintRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateComplaint(Long id, ComplaintRequestDTO dto, String userEmail) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        if (dto.getTitle() != null)
            complaint.setTitle(dto.getTitle());
        if (dto.getDescription() != null)
            complaint.setDescription(dto.getDescription());
        if (dto.getLocation() != null)
            complaint.setLocation(dto.getLocation());
        if (dto.getAttachmentUrl() != null)
            complaint.setAttachmentUrl(dto.getAttachmentUrl());

        complaint.setUpdatedAt(LocalDateTime.now());
        complaintRepository.save(complaint);

        return new ResponseEntity<>("Complaint updated successfully!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteComplaint(Long id) {
        if (!complaintRepository.existsById(id)) {
            return new ResponseEntity<>("Complaint not found!", HttpStatus.NOT_FOUND);
        }
        complaintRepository.deleteById(id);
        return new ResponseEntity<>("Complaint deleted successfully!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateComplaintStatus(Long id, UpdateComplaintStatusRequestDTO dto) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        ComplaintStatus status = complaintStatusRepository.findById(dto.getStatusId())
                .orElseThrow(() -> new RuntimeException("Status not found"));

        complaint.setStatus(status);
        complaintRepository.save(complaint);
        return new ResponseEntity<>("Status Update Successfully!!", HttpStatus.OK);

    }
}
