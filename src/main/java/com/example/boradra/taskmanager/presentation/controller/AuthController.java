package com.example.boradra.taskmanager.presentation.controller;

import com.example.boradra.taskmanager.application.dto.AuthResponse;
import com.example.boradra.taskmanager.application.dto.LoginRequest;
import com.example.boradra.taskmanager.application.dto.RegisterRequest;
import com.example.boradra.taskmanager.application.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
