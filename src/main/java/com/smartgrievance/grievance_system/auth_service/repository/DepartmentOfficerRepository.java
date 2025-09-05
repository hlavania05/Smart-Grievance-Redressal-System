package com.smartgrievance.grievance_system.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smartgrievance.grievance_system.auth_service.entity.DepartmentOfficer;
import com.smartgrievance.grievance_system.auth_service.entity.User;
import java.util.Optional;

public interface DepartmentOfficerRepository extends JpaRepository<DepartmentOfficer, Long> {
    Optional<DepartmentOfficer> findByUser(User user);   // find officer by user
}

