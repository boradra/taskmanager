package com.example.boradra.taskmanager.application.service;

import java.util.List;

import com.example.boradra.taskmanager.application.dto.TaskCreateRequest;
import com.example.boradra.taskmanager.application.dto.TaskResponse;
import com.example.boradra.taskmanager.application.dto.TaskUpdateRequest;

public interface TaskService {
    public TaskResponse createTask(TaskCreateRequest request);
    public List<TaskResponse> getAllTasks();
    public TaskResponse updateTask(Long id, TaskUpdateRequest request);
    public void deleteTask(Long id);
    public TaskResponse getTaskById(Long id);
    public TaskResponse completeTask(Long id);
}
