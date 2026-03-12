package com.example.boradra.taskmanager.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    @Test
    void testBuilder() {
        Task task = Task.builder()
                .id(1L)
                .title(new TaskTitle("Test Task"))
                .description("Test description")
                .nextExecutionDate(LocalDate.now())
                .type(TaskType.ONCE)
                .build();
        
        assertNotNull(task);
        assertEquals(1L, task.getId());
        assertEquals("Test Task", task.getTitle().getValue());
        assertEquals("Test description", task.getDescription());
        assertFalse(task.isCompleted());
    }

    @Test
    void testComplete() {
        Task task = Task.builder()
                .id(1L)
                .title(new TaskTitle("Test Task"))
                .description("This is a test task")
                .nextExecutionDate(LocalDate.now())
                .type(TaskType.ONCE)
                .build();
        task.complete();
        assertTrue(task.isCompleted());
    }

    @Test
    void testCompleteAlreadyCompletedTask() {
        Task task = Task.builder()
                .id(1L)
                .title(new TaskTitle("Test Task"))
                .description("Test description")
                .completed(true)
                .type(TaskType.ONCE)
                .build();
        
        assertThrows(IllegalStateException.class, task::complete);
    }

    @Test
    void testBuilderWithNullTitle() {
        assertThrows(NullPointerException.class, () -> {
            Task.builder()
                    .id(1L)
                    .title(null)
                    .description("Test description")
                    .type(TaskType.ONCE)
                    .build();
        });
    }

    @Test
    void testRepeatingTaskWithoutNextExecutionDate() {
        assertThrows(IllegalArgumentException.class, () -> {
            Task.builder()
                    .id(1L)
                    .title(new TaskTitle("Test Task"))
                    .description("Test description")
                    .type(TaskType.DAILY)
                    .build();
        });
    }

    @Test
    void testRepeatingTaskWithNextExecutionDate() {
        Task task = Task.builder()
                .id(1L)
                .title(new TaskTitle("Test Task"))
                .description("Test description")
                .type(TaskType.DAILY)
                .nextExecutionDate(LocalDate.now().plusDays(1))
                .build();
        
        assertNotNull(task);
        assertEquals(TaskType.DAILY, task.getType());
        assertNotNull(task.getNextExecutionDate());
    }
}