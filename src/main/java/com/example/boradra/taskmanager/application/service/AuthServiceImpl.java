package com.example.boradra.taskmanager.application.service;

import com.example.boradra.taskmanager.application.dto.AuthResponse;
import com.example.boradra.taskmanager.application.dto.LoginRequest;
import com.example.boradra.taskmanager.application.dto.RegisterRequest;
import com.example.boradra.taskmanager.application.port.TokenPort;
import com.example.boradra.taskmanager.domain.model.User;
import com.example.boradra.taskmanager.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TokenPort tokenPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        userRepository.save(user);

        return new AuthResponse(tokenPort.generateToken(user.getEmail()));
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return new AuthResponse(tokenPort.generateToken(user.getEmail()));
    }
}
