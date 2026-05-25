package com.example.boradra.taskmanager.application.port;

public interface TokenPort {

    String generateToken(String email);

    String extractEmail(String token);

    boolean isTokenValid(String token);
}
