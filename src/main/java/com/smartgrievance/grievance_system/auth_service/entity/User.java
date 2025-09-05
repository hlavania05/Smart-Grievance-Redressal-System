package com.smartgrievance.grievance_system.auth_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_details")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password; // hashed password (BCrypt)

    @Column(length = 15)
    private String phoneNumber;

    @Column(length = 255)
    private String address;

    // User <-> Role (Many-to-One)
    @ManyToOne(fetch = FetchType.EAGER) // har user ka ek role hoga
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
