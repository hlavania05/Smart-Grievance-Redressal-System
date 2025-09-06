package com.smartgrievance.grievance_system.auth_service.service.Impl;

import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.DepartmentOfficerRequestDTO;
import com.smartgrievance.grievance_system.auth_service.entity.DepartmentOfficer;
import com.smartgrievance.grievance_system.auth_service.entity.Role;
import com.smartgrievance.grievance_system.auth_service.entity.User;
import com.smartgrievance.grievance_system.auth_service.repository.DepartmentOfficerRepository;
import com.smartgrievance.grievance_system.auth_service.repository.RoleRepository;
import com.smartgrievance.grievance_system.auth_service.repository.UserRepository;
import com.smartgrievance.grievance_system.auth_service.service.DepartmentOfficerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentOfficerServiceImpl implements DepartmentOfficerService {

    @Autowired
    private DepartmentOfficerRepository officerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired 
    private RoleRepository roleRepository;

    @Override
    public ResponseEntity<?> createOfficer(DepartmentOfficerRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        DepartmentOfficer officer = new DepartmentOfficer();
        officer.setUser(user);
        officer.setDepartmentName(dto.getDepartmentName());
        officer.setDesignation(dto.getDesignation());

        officerRepository.save(officer);

        Role officerRole = roleRepository.findByRoleName("OFFICER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(officerRole);
        userRepository.save(user);

        return new ResponseEntity<>("Officer created successfully!", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getAllOfficers() {
        List<DepartmentOfficer> officers = officerRepository.findAll();
        return new ResponseEntity<>(officers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getOfficerById(Long id) {
        return officerRepository.findById(id)
                .<ResponseEntity<Object>>map(officer -> new ResponseEntity<>(officer, HttpStatus.OK))
                .orElse(new ResponseEntity<>("Officer not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<?> updateOfficer(Long id, DepartmentOfficerRequestDTO dto) {
        DepartmentOfficer officer = officerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Officer not found"));

        if (dto.getDepartmentName() != null)
            officer.setDepartmentName(dto.getDepartmentName());
        if (dto.getDesignation() != null)
            officer.setDesignation(dto.getDesignation());

        officerRepository.save(officer);

        return new ResponseEntity<>("Officer updated successfully!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteOfficer(Long id) {
        if (!officerRepository.existsById(id)) {
            return new ResponseEntity<>("Officer not found", HttpStatus.NOT_FOUND);
        }
        officerRepository.deleteById(id);
        return new ResponseEntity<>("Officer deleted successfully!", HttpStatus.OK);
    }
}
