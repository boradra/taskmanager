package com.example.boradra.taskmanager.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.boradra.taskmanager.application.applicationMapper.ApplicationMapper;
import com.example.boradra.taskmanager.application.dto.TaskCreateRequest;
import com.example.boradra.taskmanager.application.dto.TaskResponse;
import com.example.boradra.taskmanager.application.dto.TaskUpdateRequest;
import com.example.boradra.taskmanager.domain.exception.DomainTaskAlreadyExist;
import com.example.boradra.taskmanager.domain.exception.DomainTaskNotFoundException;
import com.example.boradra.taskmanager.domain.exception.InvalidTaskTypeException;
import com.example.boradra.taskmanager.domain.model.Task;
import com.example.boradra.taskmanager.domain.model.TaskTitle;
import com.example.boradra.taskmanager.domain.model.TaskType;
import com.example.boradra.taskmanager.domain.repository.TaskRepository;
import com.example.boradra.taskmanager.domain.service.TaskRepeatStrategy;
import com.example.boradra.taskmanager.infrastructure.persistence.strategy.TaskRepeatStrategyFactory;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ApplicationMapper applicationMapper;

    @Mock
    private TaskRepeatStrategyFactory taskRepeatStrategyFactory;

    @Mock
    private TaskRepeatStrategy taskRepeatStrategy;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void createTaskShouldSaveAndReturnResponseWhenRequestIsValid() {
        TaskCreateRequest request = new TaskCreateRequest();
        request.setTitle("Buy milk");
        request.setDescription("Buy two liters");
        request.setTaskType("daily");

        Task mappedTask = Task.builder()
                .title(new TaskTitle("Buy milk"))
                .description("Buy two liters")
                .type(TaskType.ONCE)
                .build();

        LocalDate nextDate = LocalDate.of(2026, 3, 18);

        Task savedTask = Task.builder()
                .id(1L)
                .title(new TaskTitle("Buy milk"))
                .description("Buy two liters")
                .type(TaskType.DAILY)
                .nextExecutionDate(nextDate)
                .build();

        TaskResponse expectedResponse = new TaskResponse();
        expectedResponse.setId(1L);
        expectedResponse.setTitle("Buy milk");
        expectedResponse.setDescription("Buy two liters");
        expectedResponse.setCompleted(false);

        when(taskRepository.existByTitle(any(TaskTitle.class))).thenReturn(false);
        when(taskRepeatStrategyFactory.getStrategy(TaskType.DAILY)).thenReturn(taskRepeatStrategy);
        when(taskRepeatStrategy.calculateNextExecutionDate(any(LocalDate.class))).thenReturn(nextDate);
        when(applicationMapper.toDomain(request)).thenReturn(mappedTask);
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);
        when(applicationMapper.toResponse(savedTask)).thenReturn(expectedResponse);

        TaskResponse actualResponse = taskService.createTask(request);

        assertNotNull(actualResponse);
        assertEquals(1L, actualResponse.getId());
        assertEquals("Buy milk", actualResponse.getTitle());

        ArgumentCaptor<Task> savedTaskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(savedTaskCaptor.capture());
        Task finalTask = savedTaskCaptor.getValue();

        assertEquals("Buy milk", finalTask.getTitle().getValue());
        assertEquals(TaskType.DAILY, finalTask.getType());
        assertEquals(nextDate, finalTask.getNextExecutionDate());
        assertFalse(finalTask.isCompleted());
    }

    @Test
    void createTaskShouldThrowWhenTitleAlreadyExists() {
        TaskCreateRequest request = new TaskCreateRequest();
        request.setTitle("Buy milk");
        request.setTaskType("once");

        when(taskRepository.existByTitle(any(TaskTitle.class))).thenReturn(true);

        assertThrows(DomainTaskAlreadyExist.class, () -> taskService.createTask(request));

        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void createTaskShouldThrowWhenTaskTypeIsInvalid() {
        TaskCreateRequest request = new TaskCreateRequest();
        request.setTitle("Buy milk");
        request.setTaskType("invalid-type");

        when(taskRepository.existByTitle(any(TaskTitle.class))).thenReturn(false);

        assertThrows(InvalidTaskTypeException.class, () -> taskService.createTask(request));

        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void getAllTasksShouldReturnMappedResponses() {
        Task firstTask = Task.builder()
                .id(1L)
                .title(new TaskTitle("Task 1"))
                .description("Description 1")
                .type(TaskType.ONCE)
                .build();

        Task secondTask = Task.builder()
                .id(2L)
                .title(new TaskTitle("Task 2"))
                .description("Description 2")
                .type(TaskType.ONCE)
                .build();

        TaskResponse firstResponse = new TaskResponse();
        firstResponse.setId(1L);
        firstResponse.setTitle("Task 1");

        TaskResponse secondResponse = new TaskResponse();
        secondResponse.setId(2L);
        secondResponse.setTitle("Task 2");

        when(taskRepository.findAll()).thenReturn(List.of(firstTask, secondTask));
        when(applicationMapper.toResponse(firstTask)).thenReturn(firstResponse);
        when(applicationMapper.toResponse(secondTask)).thenReturn(secondResponse);

        List<TaskResponse> responses = taskService.getAllTasks();

        assertEquals(2, responses.size());
        assertEquals("Task 1", responses.get(0).getTitle());
        assertEquals("Task 2", responses.get(1).getTitle());
    }

    @Test
    void updateTaskShouldSaveAndReturnUpdatedResponseWhenTaskExists() {
        TaskUpdateRequest updateRequest = new TaskUpdateRequest();
        updateRequest.setTitle("Updated title");

        Task existingTask = Task.builder()
                .id(7L)
                .title(new TaskTitle("Old title"))
                .description("Old description")
                .type(TaskType.ONCE)
                .build();

        TaskResponse expectedResponse = new TaskResponse();
        expectedResponse.setId(7L);
        expectedResponse.setTitle("Updated title");

        when(taskRepository.findById(7L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(existingTask);
        when(applicationMapper.toResponse(existingTask)).thenReturn(expectedResponse);

        TaskResponse actualResponse = taskService.updateTask(7L, updateRequest);

        assertEquals(7L, actualResponse.getId());
        verify(applicationMapper).updateDomainFromRequest(updateRequest, existingTask);
        verify(taskRepository).save(existingTask);
    }

    @Test
    void updateTaskShouldThrowWhenTaskDoesNotExist() {
        TaskUpdateRequest updateRequest = new TaskUpdateRequest();

        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(DomainTaskNotFoundException.class, () -> taskService.updateTask(99L, updateRequest));

        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void deleteTaskShouldDeleteWhenTaskExists() {
        Task existingTask = Task.builder()
                .id(10L)
                .title(new TaskTitle("Delete me"))
                .type(TaskType.ONCE)
                .build();

        when(taskRepository.findById(10L)).thenReturn(Optional.of(existingTask));

        taskService.deleteTask(10L);

        verify(taskRepository).deleteById(10L);
    }

    @Test
    void deleteTaskShouldThrowWhenTaskDoesNotExist() {
        when(taskRepository.findById(11L)).thenReturn(Optional.empty());

        assertThrows(DomainTaskNotFoundException.class, () -> taskService.deleteTask(11L));

        verify(taskRepository, never()).deleteById(any(Long.class));
    }

    @Test
    void getTaskByIdShouldReturnMappedResponseWhenTaskExists() {
        Task existingTask = Task.builder()
                .id(15L)
                .title(new TaskTitle("Read book"))
                .type(TaskType.ONCE)
                .build();

        TaskResponse expectedResponse = new TaskResponse();
        expectedResponse.setId(15L);
        expectedResponse.setTitle("Read book");

        when(taskRepository.findById(15L)).thenReturn(Optional.of(existingTask));
        when(applicationMapper.toResponse(existingTask)).thenReturn(expectedResponse);

        TaskResponse response = taskService.getTaskById(15L);

        assertEquals(15L, response.getId());
        assertEquals("Read book", response.getTitle());
    }

    @Test
    void getTaskByIdShouldThrowWhenTaskDoesNotExist() {
        when(taskRepository.findById(16L)).thenReturn(Optional.empty());

        assertThrows(DomainTaskNotFoundException.class, () -> taskService.getTaskById(16L));
    }

    @Test
    void completeTaskShouldMarkTaskAsCompletedAndSaveIt() {
        Task existingTask = Task.builder()
                .id(21L)
                .title(new TaskTitle("Finish report"))
                .completed(false)
                .type(TaskType.ONCE)
                .build();

        TaskResponse expectedResponse = new TaskResponse();
        expectedResponse.setId(21L);
        expectedResponse.setTitle("Finish report");
        expectedResponse.setCompleted(true);

        when(taskRepository.findById(21L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(existingTask);
        when(applicationMapper.toResponse(existingTask)).thenReturn(expectedResponse);

        TaskResponse response = taskService.completeTask(21L);

        assertTrue(existingTask.isCompleted());
        assertTrue(response.isCompleted());
        verify(taskRepository).save(existingTask);
    }

    @Test
    void completeTaskShouldThrowWhenTaskAlreadyCompleted() {
        Task existingTask = Task.builder()
                .id(22L)
                .title(new TaskTitle("Already done"))
                .completed(true)
                .type(TaskType.ONCE)
                .build();

        when(taskRepository.findById(22L)).thenReturn(Optional.of(existingTask));

        assertThrows(IllegalStateException.class, () -> taskService.completeTask(22L));

        verify(taskRepository, never()).save(any(Task.class));
        verify(applicationMapper, never()).toResponse(any(Task.class));
    }

    @Test
    void completeTaskShouldThrowWhenTaskDoesNotExist() {
        when(taskRepository.findById(eq(23L))).thenReturn(Optional.empty());

        assertThrows(DomainTaskNotFoundException.class, () -> taskService.completeTask(23L));

        verify(taskRepository, never()).save(any(Task.class));
    }
}
