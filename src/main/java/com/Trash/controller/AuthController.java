package com.Trash.controller;

import com.Trash.config.JwtUtil;
import com.Trash.dto.AuthResponse;
import com.Trash.dto.LoginRequest;
import com.Trash.dto.RegisterRequest;
import com.Trash.service.CustomUserDetailsService;
import com.Trash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest req) {
        userService.register(req);
        return "User Registered Successfully!";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getEmail(),
                            req.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }

        // Load user details
        UserDetails userDetails =
                customUserDetailsService.loadUserByUsername(req.getEmail());

        // 🔥 Generate JWT token WITH USER ROLE
        String token = jwtUtil.generateToken(userDetails.getUsername(), "USER");

        return new AuthResponse(token);
    }
}
