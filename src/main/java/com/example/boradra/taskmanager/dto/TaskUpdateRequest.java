package com.example.boradra.taskmanager.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class TaskUpdateRequest {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
}
