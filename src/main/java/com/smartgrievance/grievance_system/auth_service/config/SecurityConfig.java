package com.smartgrievance.grievance_system.auth_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.smartgrievance.grievance_system.auth_service.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().permitAll());

        return http.build();
        // http.csrf(csrf -> csrf.disable())
        // .authorizeHttpRequests(auth -> auth
        // .requestMatchers("/api/auth/**").permitAll()
        // .requestMatchers("/api/admin/**").hasRole("ADMIN")
        // .requestMatchers("/api/citizen/**").hasRole("CITIZEN")
        // .anyRequest().authenticated())
        // .addFilterBefore(jwtAuthenticationFilter,
        // UsernamePasswordAuthenticationFilter.class);

        // return http.build();
    }
}