package com.example.boradra.taskmanager.service;

import java.util.List;

import com.example.boradra.taskmanager.dto.*;

public interface TaskService {
    public TaskResponse createTask(TaskCreateRequest request);
    public List<TaskResponse> getAllTasks();
    public TaskResponse updateTask(Long id, TaskUpdateRequest request);

}
