package com.example.boradra.taskmanager.application.dto;


import com.example.boradra.taskmanager.domain.model.Task;
import com.example.boradra.taskmanager.domain.model.TaskType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TaskCreateRequest {
    @NotBlank(message = "Title cannot be blank, Please provide a title for the task")
    @Size(max = 100)
    private String title;
    @Size(max = 500)
    private String description;
    private String taskType;
    
}
