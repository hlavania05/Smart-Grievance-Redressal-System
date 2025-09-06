package com.smartgrievance.grievance_system.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.ComplaintCategoryRequestDTO;
import com.smartgrievance.grievance_system.auth_service.service.ComplaintCategoryService;

@RestController
@RequestMapping("/api/categories")
public class ComplaintCategoryController {

    @Autowired
    private ComplaintCategoryService categoryService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OFFICER', 'CITIZEN')")
    public ResponseEntity<?> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addCategory(@RequestBody ComplaintCategoryRequestDTO dto) {
        return categoryService.addCategory(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody ComplaintCategoryRequestDTO dto) {
        return categoryService.updateCategory(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }
}