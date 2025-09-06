package com.smartgrievance.grievance_system.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartgrievance.grievance_system.auth_service.service.ComplaintStatusService;

@RestController
@RequestMapping("/api/complaintsStatus")
public class ComplaintStatusController {
    @Autowired
    private ComplaintStatusService complaintStatusService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CITIZEN', 'OFFICER')")
    public ResponseEntity<?> getAllComplaintStatus(){
        try{
            return complaintStatusService.getAllStatuses();
        }
        catch(Exception e){
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
