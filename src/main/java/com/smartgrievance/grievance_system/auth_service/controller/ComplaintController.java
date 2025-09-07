package com.smartgrievance.grievance_system.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.ComplaintRequestDTO;
import com.smartgrievance.grievance_system.auth_service.dtos.RequestDtos.UpdateComplaintStatusRequestDTO;
import com.smartgrievance.grievance_system.auth_service.service.ComplaintService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @PostMapping
    @PreAuthorize("hasRole('CITIZEN')")
    public ResponseEntity<?> fileComplaint(@RequestBody ComplaintRequestDTO dto, Authentication auth) {
        return complaintService.fileComplaint(dto, auth.getName());
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('CITIZEN')")
    public ResponseEntity<?> getMyComplaints(Authentication auth) {
        return complaintService.getMyComplaints(auth.getName());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getComplaintById(@PathVariable Long id) {
        return complaintService.getComplaintById(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllComplaints() {
        return complaintService.getAllComplaints();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CITIZEN')")
    public ResponseEntity<?> updateComplaint(@PathVariable Long id, @RequestBody ComplaintRequestDTO dto, Authentication auth) {
        return complaintService.updateComplaint(id, dto, auth.getName());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CITIZEN')")
    public ResponseEntity<?> deleteComplaint(@PathVariable Long id) {
        return complaintService.deleteComplaint(id);
    }

    @PutMapping("/complaint/{id}")
    @PreAuthorize("hasRole('OFFICER')")
    public ResponseEntity<?> updateComplaintStatus(@PathVariable Long id, @RequestBody UpdateComplaintStatusRequestDTO updateComplaintStatusRequest){
        try{
            return complaintService.updateComplaintStatus(id, updateComplaintStatusRequest);
        }
        catch(Exception e){
            return new ResponseEntity<>("Intenal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
