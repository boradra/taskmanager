package com.example.boradra.taskmanager.controller;

import java.util.List;

import org.springframework.stereotype.Controller;

import com.example.boradra.taskmanager.dto.TaskCreateRequest;
import com.example.boradra.taskmanager.dto.TaskResponse;
import com.example.boradra.taskmanager.dto.TaskUpdateRequest;
import com.example.boradra.taskmanager.service.TaskService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
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
    
    
}
