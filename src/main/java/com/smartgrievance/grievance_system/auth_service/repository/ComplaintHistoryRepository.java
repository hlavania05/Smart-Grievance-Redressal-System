package com.smartgrievance.grievance_system.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartgrievance.grievance_system.auth_service.entity.Complaint;
import com.smartgrievance.grievance_system.auth_service.entity.ComplaintHistory;

import java.util.List;

public interface ComplaintHistoryRepository extends JpaRepository<ComplaintHistory, Long> {
    List<ComplaintHistory> findByComplaintOrderByUpdatedAtDesc(Complaint complaint);
}
