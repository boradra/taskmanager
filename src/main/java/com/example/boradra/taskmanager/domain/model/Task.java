package com.example.boradra.taskmanager.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.val;

import java.time.LocalDate;
import java.util.Objects;

@Getter
public class Task {
    private Long id;
    private TaskTitle title;
    private String description;
    private boolean completed;
    private TaskType type;             
    private LocalDate nextExecutionDate; 

    @Builder
    private Task(Long id, TaskTitle title, String description, boolean completed, TaskType type, LocalDate nextExecutionDate) {

        validateNextExecutionDate();
        this.id = id;
        this.title = Objects.requireNonNull(title, "Task title cannot be null");
        this.description = description;
        this.completed = completed;
        this.type = (type != null) ? type : TaskType.ONCE; 
        this.nextExecutionDate = nextExecutionDate;
    }

    public void complete() {
       if(this.completed) {
            throw new IllegalStateException("Task is already completed.");
       }
       this.completed = true;
    }

    private void validateNextExecutionDate() {
        if (type != null && type != TaskType.ONCE && nextExecutionDate == null) {
            throw new IllegalArgumentException("Next execution date must be provided for repeating tasks.");
        }
    }
}