package com.smartgrievance.grievance_system.auth_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/admin")
public class ComplaintController {

    @GetMapping("/allcomplaints")
    public ResponseEntity<?> getAllComplaints(){
        return new ResponseEntity<>("Hello this is admin portal", HttpStatus.OK);
    }
}
