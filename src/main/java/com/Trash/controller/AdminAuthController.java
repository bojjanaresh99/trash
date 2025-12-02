package com.Trash.controller;

import com.Trash.config.JwtUtil;
import com.Trash.dto.AdminLoginRequest;
import com.Trash.dto.AdminRegisterRequest;
import com.Trash.dto.AuthResponse;
import com.Trash.entity.Admin;
import com.Trash.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/admin/auth")
@Tag(name = "Admin Authentication APIs", description = "Register & Login for Admins")
public class AdminAuthController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "Register new admin")
    @PostMapping("/register")
    public String register(@RequestBody AdminRegisterRequest req) {
        adminService.registerAdmin(req);
        return "Admin Registered Successfully!";
    }

    @Operation(summary = "Admin Login", description = "Returns JWT Token")
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AdminLoginRequest req) {

        // 1️⃣ Find admin by email
        Admin admin = adminService.findByEmail(req.getEmail());

        // 2️⃣ Match password manually because admin is NOT in UserDetailsService
        if (!passwordEncoder.matches(req.getPassword(), admin.getPassword())) {
            throw new BadCredentialsException("Invalid email or password for Admin");
        }

        // 3️⃣ Generate JWT token using admin email
        String token = jwtUtil.generateToken(admin.getEmail());

        // 4️⃣ Return token response
        return new AuthResponse(token);
    }
}
