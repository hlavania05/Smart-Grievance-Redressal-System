package com.smartgrievance.grievance_system.auth_service.service;

import org.springframework.http.ResponseEntity;

import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.DepartmentOfficerRequestDTO;


public interface DepartmentOfficerService {
    ResponseEntity<?> createOfficer(DepartmentOfficerRequestDTO dto);
    ResponseEntity<?> getAllOfficers();
    ResponseEntity<?> getOfficerById(Long id);
    ResponseEntity<?> updateOfficer(Long id, DepartmentOfficerRequestDTO dto);
    ResponseEntity<?> deleteOfficer(Long id);
}
