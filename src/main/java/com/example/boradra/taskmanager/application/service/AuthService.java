package com.example.boradra.taskmanager.application.service;

import com.example.boradra.taskmanager.application.dto.AuthResponse;
import com.example.boradra.taskmanager.application.dto.LoginRequest;
import com.example.boradra.taskmanager.application.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
