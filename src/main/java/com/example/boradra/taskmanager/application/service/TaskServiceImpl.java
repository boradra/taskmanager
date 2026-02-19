package com.example.boradra.taskmanager.application.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.example.boradra.taskmanager.application.dto.TaskCreateRequest;
import com.example.boradra.taskmanager.application.dto.TaskResponse;
import com.example.boradra.taskmanager.application.dto.TaskUpdateRequest;
import com.example.boradra.taskmanager.domain.model.Task;
import com.example.boradra.taskmanager.domain.repository.TaskRepository;
import com.example.boradra.taskmanager.application.applicationMapper.ApplicationMapper;
import com.example.boradra.taskmanager.domain.exception.DomainTaskNotFoundException;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ApplicationMapper taskDtoMapper;
    public TaskServiceImpl(TaskRepository taskRepository, ApplicationMapper taskDtoMapper) {
        this.taskRepository = taskRepository;
        this.taskDtoMapper = taskDtoMapper;
    }

    public TaskResponse createTask(TaskCreateRequest request) {
        Task task = taskDtoMapper.toDomain(request);
        Task savedTask = taskRepository.save(task);
        TaskResponse response = taskDtoMapper.toResponse(savedTask);
        return response;  
    }

    public List<TaskResponse> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        List<TaskResponse> responses = tasks.stream().map(task -> {
            TaskResponse response = taskDtoMapper.toResponse(task);
            return response;
        }).toList();
        return responses;
    }

    public TaskResponse updateTask(Long id,TaskUpdateRequest request)
        {
            Task task = taskRepository.findById(id).orElseThrow(() -> new DomainTaskNotFoundException("Task not found"));
            taskDtoMapper.updateDomainFromRequest(request, task);
            Task updatedTask = taskRepository.save(task);
            TaskResponse response = taskDtoMapper.toResponse(updatedTask);
            return response;
        }
    public void deleteTask(Long id) {
        taskRepository.findById(id).orElseThrow(() -> new DomainTaskNotFoundException("Task not found"));
        taskRepository.deleteById(id);
    }

    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new DomainTaskNotFoundException("Task not found"));
        TaskResponse response = taskDtoMapper.toResponse(task);
        return response;
    }

   
}
