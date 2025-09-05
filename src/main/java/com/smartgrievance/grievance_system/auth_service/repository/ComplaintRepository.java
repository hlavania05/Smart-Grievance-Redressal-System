package com.smartgrievance.grievance_system.auth_service.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.smartgrievance.grievance_system.auth_service.entity.Complaint;
import com.smartgrievance.grievance_system.auth_service.entity.User;
import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByUser(User user); 
}
