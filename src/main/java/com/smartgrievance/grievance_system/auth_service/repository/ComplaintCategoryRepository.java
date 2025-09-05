package com.smartgrievance.grievance_system.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smartgrievance.grievance_system.auth_service.entity.ComplaintCategory;
import java.util.Optional;

public interface ComplaintCategoryRepository extends JpaRepository<ComplaintCategory, Long> {
    Optional<ComplaintCategory> findByCategoryName(String categoryName);
}

