package com.example.boradra.taskmanager.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.example.boradra.taskmanager.entity.Task;
import com.example.boradra.taskmanager.dto.TaskCreateRequest;
import com.example.boradra.taskmanager.dto.TaskResponse;
import com.example.boradra.taskmanager.dto.TaskUpdateRequest;

import com.example.boradra.taskmanager.exception.TaskNotFoundException;
import com.example.boradra.taskmanager.repository.TaskRepository;
import com.example.boradra.taskmanager.taskMapper.TaskMapper;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public TaskResponse createTask(TaskCreateRequest request) {
        Task task = taskMapper.toTask(request);
        Task savedTask = taskRepository.save(task);
        TaskResponse response = taskMapper.toTaskResponse(savedTask);
        return response;  
    }

    public List<TaskResponse> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        List<TaskResponse> responses = tasks.stream().map(task -> {
            TaskResponse response = taskMapper.toTaskResponse(task);
            return response;
        }).toList();
        return responses;
    }

    public TaskResponse updateTask(Long id,TaskUpdateRequest request)
        {
            Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
            taskMapper.updateTaskFromRequest(request, task);
            Task updatedTask = taskRepository.save(task);
            TaskResponse response = taskMapper.toTaskResponse(updatedTask);
            return response;
        }
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        taskRepository.delete(task);
    }

    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        TaskResponse response = taskMapper.toTaskResponse(task);
        return response;
    }

   
}
