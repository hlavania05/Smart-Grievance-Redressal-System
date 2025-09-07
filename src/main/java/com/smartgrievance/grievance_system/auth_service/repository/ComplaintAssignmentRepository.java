package com.smartgrievance.grievance_system.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smartgrievance.grievance_system.auth_service.entity.ComplaintAssignment;

import java.util.List;

public interface ComplaintAssignmentRepository extends JpaRepository<ComplaintAssignment, Long> {
    List<ComplaintAssignment> findByOfficerOfficerId(Long officerId);
    List<ComplaintAssignment> findByComplaintComplaintId(Long complaintId);
}

