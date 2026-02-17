package com.example.boradra.taskmanager.application.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.example.boradra.taskmanager.application.dto.TaskCreateRequest;
import com.example.boradra.taskmanager.application.dto.TaskResponse;
import com.example.boradra.taskmanager.application.dto.TaskUpdateRequest;
import com.example.boradra.taskmanager.domain.model.Task;
import com.example.boradra.taskmanager.domain.repository.TaskRepository;
import com.example.boradra.taskmanager.infrastructure.persistence.mapper.TaskMapper;
import com.example.boradra.taskmanager.domain.exception.DomainTaskNotFoundException;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public TaskResponse createTask(TaskCreateRequest request) {
        Task task = taskMapper.toDomain(request);
        Task savedTask = taskRepository.save(task);
        TaskResponse response = taskMapper.toResponse(savedTask);
        return response;  
    }

    public List<TaskResponse> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        List<TaskResponse> responses = tasks.stream().map(task -> {
            TaskResponse response = taskMapper.toResponse(task);
            return response;
        }).toList();
        return responses;
    }

    public TaskResponse updateTask(Long id,TaskUpdateRequest request)
        {
            Task task = taskRepository.findById(id).orElseThrow(() -> new DomainTaskNotFoundException("Task not found"));
            taskMapper.updateDomainFromRequest(request, task);
            Task updatedTask = taskRepository.save(task);
            TaskResponse response = taskMapper.toResponse(updatedTask);
            return response;
        }
    public void deleteTask(Long id) {
        taskRepository.findById(id).orElseThrow(() -> new DomainTaskNotFoundException("Task not found"));
        taskRepository.deleteById(id);
    }

    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new DomainTaskNotFoundException("Task not found"));
        TaskResponse response = taskMapper.toResponse(task);
        return response;
    }

   
}
