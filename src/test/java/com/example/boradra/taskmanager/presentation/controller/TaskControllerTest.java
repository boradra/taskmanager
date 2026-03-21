package com.example.boradra.taskmanager.presentation.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.boradra.taskmanager.application.dto.TaskCreateRequest;
import com.example.boradra.taskmanager.application.dto.TaskResponse;
import com.example.boradra.taskmanager.application.dto.TaskUpdateRequest;
import com.example.boradra.taskmanager.application.service.TaskService;
import com.example.boradra.taskmanager.domain.exception.DomainTaskAlreadyExist;
import com.example.boradra.taskmanager.domain.exception.DomainTaskNotFoundException;
import com.example.boradra.taskmanager.domain.exception.InvalidTaskTypeException;
import com.example.boradra.taskmanager.presentation.exception.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createTaskShouldReturnTaskResponse() throws Exception {
        TaskCreateRequest request = new TaskCreateRequest();
        request.setTitle("Buy milk");
        request.setDescription("2 liters");
        request.setTaskType("daily");

        TaskResponse response = new TaskResponse();
        response.setId(1L);
        response.setTitle("Buy milk");
        response.setDescription("2 liters");
        response.setCompleted(false);

        when(taskService.createTask(any(TaskCreateRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Buy milk"));

        verify(taskService).createTask(any(TaskCreateRequest.class));
    }

    @Test
    void createTaskShouldReturnConflictWhenTaskAlreadyExists() throws Exception {
        TaskCreateRequest request = new TaskCreateRequest();
        request.setTitle("Buy milk");
        request.setTaskType("once");

        when(taskService.createTask(any(TaskCreateRequest.class)))
                .thenThrow(new DomainTaskAlreadyExist("Task already exists"));

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Task already exists"))
                .andExpect(jsonPath("$.statusCode").value(409));
    }

            @Test
            void createTaskShouldReturnBadRequestWhenTaskTypeIsInvalid() throws Exception {
            TaskCreateRequest request = new TaskCreateRequest();
            request.setTitle("Buy milk");
            request.setTaskType("invalid-type");

            when(taskService.createTask(any(TaskCreateRequest.class)))
                .thenThrow(new InvalidTaskTypeException("Invalid task type: invalid-type"));

            mockMvc.perform(post("/api/tasks")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid task type: invalid-type"))
                .andExpect(jsonPath("$.statusCode").value(400));
            }

    @Test
    void getAllTasksShouldReturnTaskList() throws Exception {
        TaskResponse firstTask = new TaskResponse();
        firstTask.setId(1L);
        firstTask.setTitle("Task 1");

        TaskResponse secondTask = new TaskResponse();
        secondTask.setId(2L);
        secondTask.setTitle("Task 2");

        when(taskService.getAllTasks()).thenReturn(List.of(firstTask, secondTask));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].title").value("Task 2"));

        verify(taskService).getAllTasks();
    }

    @Test
    void updateTaskShouldReturnUpdatedTask() throws Exception {
        TaskUpdateRequest request = new TaskUpdateRequest();
        request.setTitle("Updated title");

        TaskResponse response = new TaskResponse();
        response.setId(7L);
        response.setTitle("Updated title");

        when(taskService.updateTask(eq(7L), any(TaskUpdateRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/tasks/path/7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7L))
                .andExpect(jsonPath("$.title").value("Updated title"));

        verify(taskService).updateTask(eq(7L), any(TaskUpdateRequest.class));
    }

    @Test
    void updateTaskShouldReturnNotFoundWhenTaskDoesNotExist() throws Exception {
        TaskUpdateRequest request = new TaskUpdateRequest();
        request.setTitle("Updated title");

        when(taskService.updateTask(eq(99L), any(TaskUpdateRequest.class)))
                .thenThrow(new DomainTaskNotFoundException("Task not found"));

        mockMvc.perform(put("/api/tasks/path/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Task not found"))
                .andExpect(jsonPath("$.statusCode").value(404));
    }

    @Test
    void deleteTaskShouldCallService() throws Exception {
        mockMvc.perform(delete("/api/tasks/10"))
                .andExpect(status().isOk());

        verify(taskService).deleteTask(10L);
    }

        @Test
        void deleteTaskShouldReturnNotFoundWhenTaskDoesNotExist() throws Exception {
        org.mockito.Mockito.doThrow(new DomainTaskNotFoundException("Task not found"))
            .when(taskService)
            .deleteTask(11L);

        mockMvc.perform(delete("/api/tasks/11"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Task not found"))
            .andExpect(jsonPath("$.statusCode").value(404));

        verify(taskService).deleteTask(11L);
        }

    @Test
    void getTaskByIdShouldReturnTask() throws Exception {
        TaskResponse response = new TaskResponse();
        response.setId(15L);
        response.setTitle("Read book");

        when(taskService.getTaskById(15L)).thenReturn(response);

        mockMvc.perform(get("/api/tasks/15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(15L))
                .andExpect(jsonPath("$.title").value("Read book"));

        verify(taskService).getTaskById(15L);
    }

    @Test
    void getTaskByIdShouldReturnNotFoundWhenTaskDoesNotExist() throws Exception {
        when(taskService.getTaskById(16L)).thenThrow(new DomainTaskNotFoundException("Task not found"));

        mockMvc.perform(get("/api/tasks/16"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Task not found"))
                .andExpect(jsonPath("$.statusCode").value(404));
    }

    @Test
    void completeTaskShouldReturnCompletedTask() throws Exception {
        TaskResponse response = new TaskResponse();
        response.setId(21L);
        response.setTitle("Finish report");
        response.setCompleted(true);

        when(taskService.completeTask(21L)).thenReturn(response);

        mockMvc.perform(put("/api/tasks/path/complete/21"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(21L))
                .andExpect(jsonPath("$.completed").value(true));

        verify(taskService).completeTask(21L);
    }

    @Test
    void completeTaskShouldReturnInternalServerErrorWhenTaskAlreadyCompleted() throws Exception {
        when(taskService.completeTask(22L)).thenThrow(new IllegalStateException("Task is already completed"));

        mockMvc.perform(put("/api/tasks/path/complete/22"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Task is already completed"))
                .andExpect(jsonPath("$.statusCode").value(500));

        verify(taskService).completeTask(22L);
    }

    @Test
    void completeTaskShouldReturnNotFoundWhenTaskDoesNotExist() throws Exception {
        when(taskService.completeTask(23L)).thenThrow(new DomainTaskNotFoundException("Task not found"));

        mockMvc.perform(put("/api/tasks/path/complete/23"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Task not found"))
                .andExpect(jsonPath("$.statusCode").value(404));

        verify(taskService).completeTask(23L);
    }

    @Test
    void createTaskShouldNotCallServiceWhenTitleValidationFails() throws Exception {
        TaskCreateRequest request = new TaskCreateRequest();
        request.setTitle("");
        request.setDescription("2 liters");
        request.setTaskType("daily");

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(taskService, never()).createTask(any(TaskCreateRequest.class));
    }
}
