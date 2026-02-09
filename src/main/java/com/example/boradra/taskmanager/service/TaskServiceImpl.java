package com.example.boradra.taskmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.example.boradra.taskmanager.entity.Task;
import com.example.boradra.taskmanager.dto.TaskCreateRequest;
import com.example.boradra.taskmanager.dto.TaskResponse;
import com.example.boradra.taskmanager.dto.TaskUpdateRequest;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.example.boradra.taskmanager.exception.TaskNotFoundException;
import com.example.boradra.taskmanager.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponse createTask(TaskCreateRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());

        Task savedTask = taskRepository.save(task);

        TaskResponse response = new TaskResponse();
        response.setId(savedTask.getId());
        response.setTitle(savedTask.getTitle());
        response.setDescription(savedTask.getDescription());
        return response;  
    }

    public List<TaskResponse> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        List<TaskResponse> responses = tasks.stream().map(task -> {
            TaskResponse response = new TaskResponse();
            response.setId(task.getId());
            response.setTitle(task.getTitle());
            response.setDescription(task.getDescription());
            response.setCompleted(task.isCompleted());
            return response;
        }).toList();
        return responses;
    }

    public TaskResponse updateTask(Long id,TaskUpdateRequest request)
        {
            Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
            task.setTitle(request.getTitle());
            task.setDescription(request.getDescription());
            task.setCompleted(request.isCompleted());
            Task updatedTask = taskRepository.save(task);

            TaskResponse response = new TaskResponse();
            response.setId(updatedTask.getId());
            response.setTitle(updatedTask.getTitle());
            response.setDescription(updatedTask.getDescription());
            response.setCompleted(updatedTask.isCompleted());   
            
            return response;

        }
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        taskRepository.delete(task);
    }

   
}
