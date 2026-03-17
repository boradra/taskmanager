package com.example.boradra.taskmanager.domain.exception;

public class InvalidTaskTypeException extends RuntimeException {
    public InvalidTaskTypeException(String message) {
        super(message);
    }

}
