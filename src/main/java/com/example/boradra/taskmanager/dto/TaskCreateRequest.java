package com.example.boradra.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TaskCreateRequest {
    @NotBlank(message = "Başlık boş olamaz")
    @Size(max = 100)
    private String title;
    @Size(max = 500)
    private String description;
}
