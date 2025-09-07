package com.smartgrievance.grievance_system.auth_service.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.ComplaintCategoryRequestDTO;
import com.smartgrievance.grievance_system.auth_service.entity.ComplaintCategory;
import com.smartgrievance.grievance_system.auth_service.repository.ComplaintCategoryRepository;
import com.smartgrievance.grievance_system.auth_service.service.ComplaintCategoryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ComplaintCategoryServiceImpl implements ComplaintCategoryService {

    @Autowired
    private ComplaintCategoryRepository categoryRepository;

    @Override
    public ResponseEntity<?> getAllCategories() {
        List<ComplaintCategory> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("Message: ", "No Categories are Available!!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addCategory(ComplaintCategoryRequestDTO dto) {
        if (categoryRepository.findByCategoryName(dto.getCategoryName()).orElse(null) != null) {
            return new ResponseEntity<>("Category already exists!", HttpStatus.CONFLICT);
        }
        ComplaintCategory category = new ComplaintCategory();
        category.setCategoryName(dto.getCategoryName());

        categoryRepository.save(category);
        return new ResponseEntity<>("Category added successfully!", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updateCategory(Long id, ComplaintCategoryRequestDTO dto) {
        ComplaintCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found!"));

        if (categoryRepository.findByCategoryName(dto.getCategoryName()).orElse(null) != null
                && !category.getCategoryName().equals(dto.getCategoryName())) {
            return new ResponseEntity<>("Category name already exists!", HttpStatus.CONFLICT);
        }

        category.setCategoryName(dto.getCategoryName());
        ComplaintCategory updatedCategory = categoryRepository.save(category);

        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            return new ResponseEntity<>("Category not found!", HttpStatus.NOT_FOUND);
        }

        try {
            categoryRepository.deleteById(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Category deleted successfully!");
            response.put("deletedId", String.valueOf(id));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Unable to delete category. It may be in use.", HttpStatus.CONFLICT);
        }
    }
}
