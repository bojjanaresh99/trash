package com.Trash.service;

import com.Trash.dto.AdminRegisterRequest;
import com.Trash.entity.Admin;
import com.Trash.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    public Admin registerAdmin(AdminRegisterRequest req) {

        if (repo.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Admin email already exists!");
        }

        Admin admin = new Admin();
        admin.setEmail(req.getEmail());
        admin.setPassword(encoder.encode(req.getPassword()));
        admin.setRole("ADMIN");

        return repo.save(admin);
    }

    public Admin findByEmail(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
    }
}
