package com.example.boradra.taskmanager.domain.exception;

public class DomainTaskNotFoundException extends RuntimeException {
    public DomainTaskNotFoundException(String message) {
        super(message);
    }
}
