package com.example.boradra.taskmanager.domain.model;



// com.example.boradra.taskmanager.domain.model
public class Task {
    private Long id;
    private TaskTitle title;
    private String description;
    private boolean completed;

    public Long getId() {
        return id;
    }
    public TaskTitle getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public boolean isCompleted() {
        return completed;
    }
    @SuppressWarnings("unused")
    private void setId(Long id) {
        this.id = id;
    }
    @SuppressWarnings("unused")
    private void setTitle(TaskTitle title) {
        this.title = title;
    }
    @SuppressWarnings("unused")
    private void setDescription(String description) {
        this.description = description;
    }
    @SuppressWarnings("unused")
    private void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void complete() {
       if(this.completed)
       {
            throw new IllegalStateException("Mission already completed.");
       }
       this.completed = true;
    }
    
}
