package com.smartgrievance.grievance_system.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smartgrievance.grievance_system.auth_service.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);  
    Optional<User> findByUserId(Long id);
}
