package com.example.boradra.taskmanager.application.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
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
import com.example.boradra.taskmanager.domain.exception.DomainTaskAlreadyExist;
import com.example.boradra.taskmanager.domain.exception.InvalidTaskTypeException;

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
    if (taskRepository.existByTitle(titleToRequest)) {
        throw new DomainTaskAlreadyExist("Task with title '" + request.getTitle() + "' already exists");
    }

    TaskType type;
    try {
        String rawType = (request.getTaskType() == null || request.getTaskType().isBlank())
                ? "ONCE"
                : request.getTaskType();
        type = TaskType.valueOf(rawType.toUpperCase(Locale.ROOT));
    } catch (IllegalArgumentException e) {
        throw new InvalidTaskTypeException("Invalid task type: " + request.getTaskType());
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

    public TaskResponse updateTask(Long id, TaskUpdateRequest request) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new DomainTaskNotFoundException("Task not found"));

        TaskTitle newTitle = request.getTitle() != null ? new TaskTitle(request.getTitle()) : task.getTitle();
        String newDescription = request.getDescription() != null ? request.getDescription() : task.getDescription();

        Task updatedTask = Task.builder()
                .id(task.getId())
                .title(newTitle)
                .description(newDescription)
                .completed(task.isCompleted())
                .type(task.getType())
                .nextExecutionDate(task.getNextExecutionDate())
                .build();

        Task savedTask = taskRepository.save(updatedTask);
        return taskDtoMapper.toResponse(savedTask);
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
