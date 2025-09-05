package com.smartgrievance.grievance_system.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smartgrievance.grievance_system.auth_service.entity.Role;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);
}

