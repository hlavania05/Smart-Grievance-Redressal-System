package com.smartgrievance.grievance_system.auth_service.service;

import org.springframework.http.ResponseEntity;

import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.ComplaintCategoryRequestDTO;

public interface ComplaintCategoryService {
    ResponseEntity<?> getAllCategories();
    ResponseEntity<?> addCategory(ComplaintCategoryRequestDTO dto);
    ResponseEntity<?> updateCategory(Long id, ComplaintCategoryRequestDTO dto);
    ResponseEntity<?> deleteCategory(Long id);
}
