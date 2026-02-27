package com.example.boradra.taskmanager.application.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

import com.example.boradra.taskmanager.application.dto.TaskCreateRequest;
import com.example.boradra.taskmanager.application.dto.TaskResponse;
import com.example.boradra.taskmanager.application.dto.TaskUpdateRequest;
import com.example.boradra.taskmanager.domain.model.Task;
import com.example.boradra.taskmanager.domain.model.TaskTitle;
import com.example.boradra.taskmanager.domain.repository.TaskRepository;
import com.example.boradra.taskmanager.domain.service.TaskRepeatStrategy;
import com.example.boradra.taskmanager.application.applicationMapper.ApplicationMapper;
import com.example.boradra.taskmanager.domain.exception.DomainTaskNotFoundException;
import com.example.boradra.taskmanager.domain.model.TaskType;
import com.example.boradra.taskmanager.infrastructure.persistence.strategy.TaskRepeatStrategyFactory;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ApplicationMapper taskDtoMapper;
    private final TaskRepeatStrategyFactory taskRepeatStrategyFactory;
    public TaskServiceImpl(TaskRepository taskRepository, ApplicationMapper taskDtoMapper, TaskRepeatStrategyFactory taskRepeatStrategyFactory) {
        this.taskRepository = taskRepository;
        this.taskDtoMapper = taskDtoMapper;
        this.taskRepeatStrategyFactory = taskRepeatStrategyFactory;
    }

    public TaskResponse createTask(TaskCreateRequest request) {

    TaskTitle titleToRequest = new TaskTitle(request.getTitle());
    /*if (taskRepository.existByTitle(titleToRequest)) {
        throw new RuntimeException("Bu başlığa sahip bir görev zaten mevcut.");
    }
        */
    TaskType type;
    try {
        type = TaskType.valueOf(request.getTaskType().toUpperCase());
    } catch (IllegalArgumentException e) {
        throw new RuntimeException("Invalid task type: " + request.getTaskType());
    }
    
    TaskRepeatStrategy strategy = taskRepeatStrategyFactory.getStrategy(type);
    LocalDate nextDate = strategy.calculateNextExecutionDate(LocalDate.now());

    Task taskFromDto = taskDtoMapper.toDomain(request);

    Task finalTask = Task.builder()
            .id(taskFromDto.getId()) 
            .title(titleToRequest)
            .description(taskFromDto.getDescription())
            .completed(false)
            .type(type) 
            .nextExecutionDate(nextDate) 
            .build();

    Task savedTask = taskRepository.save(finalTask);
    return taskDtoMapper.toResponse(savedTask);
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

    public TaskResponse completeTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new DomainTaskNotFoundException("Task not found"));
        task.complete();
        taskRepository.save(task);
        return taskDtoMapper.toResponse(task);
       
    }

   
}
