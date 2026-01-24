package com.example.boradra.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskCreateRequest {
    @NotBlank
    @Size(max = 100)
    private String title;
    @Size(max = 500)
    private String description;
}
