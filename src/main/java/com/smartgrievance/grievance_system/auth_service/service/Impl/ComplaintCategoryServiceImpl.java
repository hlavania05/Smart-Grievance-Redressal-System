package com.smartgrievance.grievance_system.auth_service.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.ComplaintCategoryRequestDTO;
import com.smartgrievance.grievance_system.auth_service.entity.ComplaintCategory;
import com.smartgrievance.grievance_system.auth_service.repository.ComplaintCategoryRepository;
import com.smartgrievance.grievance_system.auth_service.service.ComplaintCategoryService;

import java.util.List;
import java.util.Optional;

@Service
public class ComplaintCategoryServiceImpl implements ComplaintCategoryService {

    @Autowired
    private ComplaintCategoryRepository categoryRepository;

    @Override
    public ResponseEntity<?> getAllCategories() {
        List<ComplaintCategory> categories = categoryRepository.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addCategory(ComplaintCategoryRequestDTO dto) {
        ComplaintCategory category = new ComplaintCategory();
        category.setCategoryName(dto.getCategoryName());

        categoryRepository.save(category);
        return new ResponseEntity<>("Category added successfully!", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updateCategory(Long id, ComplaintCategoryRequestDTO dto) {
        Optional<ComplaintCategory> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            return new ResponseEntity<>("Category not found!", HttpStatus.NOT_FOUND);
        }

        ComplaintCategory category = optionalCategory.get();
        category.setCategoryName(dto.getCategoryName());

        categoryRepository.save(category);
        return new ResponseEntity<>("Category updated successfully!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteCategory(Long id) {
        Optional<ComplaintCategory> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            return new ResponseEntity<>("Category not found!", HttpStatus.NOT_FOUND);
        }

        categoryRepository.deleteById(id);
        return new ResponseEntity<>("Category deleted successfully!", HttpStatus.OK);
    }
}

