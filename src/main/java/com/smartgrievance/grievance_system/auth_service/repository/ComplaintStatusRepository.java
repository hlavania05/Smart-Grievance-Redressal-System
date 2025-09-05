package com.smartgrievance.grievance_system.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smartgrievance.grievance_system.auth_service.entity.ComplaintStatus;
import java.util.Optional;

public interface ComplaintStatusRepository extends JpaRepository<ComplaintStatus, Long> {
    Optional<ComplaintStatus> findByStatusName(String statusName);
}
