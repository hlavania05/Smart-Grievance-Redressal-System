package com.smartgrievance.grievance_system.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.DepartmentOfficerRequestDTO;
import com.smartgrievance.grievance_system.auth_service.service.DepartmentOfficerService;

@RestController
@RequestMapping("/api/officers")
public class DepartmentOfficerController {

    @Autowired
    private DepartmentOfficerService officerService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createOfficer(@RequestBody DepartmentOfficerRequestDTO dto) {
        return officerService.createOfficer(dto);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllOfficers() {
        return officerService.getAllOfficers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN', 'OFFICER')")
    public ResponseEntity<?> getOfficerById(@PathVariable Long id) {
        return officerService.getOfficerById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateOfficer(@PathVariable Long id, @RequestBody DepartmentOfficerRequestDTO dto) {
        return officerService.updateOfficer(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteOfficer(@PathVariable Long id) {
        return officerService.deleteOfficer(id);
    }
}

