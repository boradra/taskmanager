package com.example.boradra.taskmanager.presentation.controller;

import java.util.List;

import com.example.boradra.taskmanager.application.dto.TaskCreateRequest;
import com.example.boradra.taskmanager.application.dto.TaskResponse;
import com.example.boradra.taskmanager.application.dto.TaskUpdateRequest;
import com.example.boradra.taskmanager.application.service.TaskService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;





@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping()
    public TaskResponse taskCreateRequest(@Valid @RequestBody TaskCreateRequest entity) {  
        return taskService.createTask(entity);     
        
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public List<TaskResponse> taskResponse() {
        return taskService.getAllTasks();
    }

    @PutMapping("/path/{id}")
    public TaskResponse taskUpdateRequest(@PathVariable Long id, @RequestBody TaskUpdateRequest entity) {
       
        return taskService.updateTask(id, entity);
    }
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PutMapping("/path/complete/{id}")
    public TaskResponse completeTask(@PathVariable Long id) {
        return taskService.completeTask(id);

    
    }
    
}
