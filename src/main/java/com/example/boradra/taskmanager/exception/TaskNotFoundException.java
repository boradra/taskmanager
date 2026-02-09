package com.example.boradra.taskmanager.exception;

import com.example.boradra.taskmanager.entity.Task;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
