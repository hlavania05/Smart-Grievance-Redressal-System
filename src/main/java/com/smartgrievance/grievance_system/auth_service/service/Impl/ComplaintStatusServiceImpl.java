package com.smartgrievance.grievance_system.auth_service.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.smartgrievance.grievance_system.auth_service.entity.ComplaintStatus;
import com.smartgrievance.grievance_system.auth_service.repository.ComplaintStatusRepository;
import com.smartgrievance.grievance_system.auth_service.service.ComplaintStatusService;

@Service
public class ComplaintStatusServiceImpl implements ComplaintStatusService {
    @Autowired
    private ComplaintStatusRepository statusRepository;

    @Override
    public ResponseEntity<?> getAllStatuses() {
        List<ComplaintStatus> statuses = statusRepository.findAll();
        if(statuses.size() == 0){
            return new ResponseEntity<>("No Complaint Status is there!", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(statuses, HttpStatus.OK);
    }
}
