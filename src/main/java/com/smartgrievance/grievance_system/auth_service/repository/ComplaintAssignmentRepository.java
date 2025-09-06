package com.smartgrievance.grievance_system.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartgrievance.grievance_system.auth_service.entity.Complaint;
import com.smartgrievance.grievance_system.auth_service.entity.ComplaintAssignment;
import com.smartgrievance.grievance_system.auth_service.entity.DepartmentOfficer;

import java.util.List;

public interface ComplaintAssignmentRepository extends JpaRepository<ComplaintAssignment, Long> {
    List<ComplaintAssignment> findByOfficer(DepartmentOfficer officer);  // officer ke assignments
    List<ComplaintAssignment> findByComplaint(Complaint complaint);      // ek complaint ke assignments
    List<ComplaintAssignment> findByOfficerOfficerId(Long officerId);
    List<ComplaintAssignment> findByComplaintComplaintId(Long complaintId);
}

