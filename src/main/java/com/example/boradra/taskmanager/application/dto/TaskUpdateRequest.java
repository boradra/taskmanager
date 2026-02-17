package com.example.boradra.taskmanager.application.dto;


import lombok.Data;

@Data
public class TaskUpdateRequest {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
}
