package com.example.boradra.taskmanager.domain.exception;

public class DomainTaskAlreadyExist extends RuntimeException {
    public DomainTaskAlreadyExist(String message) {
        super(message);
    }

}
