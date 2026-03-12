package com.example.boradra.taskmanager.domain.model;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
public class TaskTitleTest {
    TaskTitle taskTitle = new TaskTitle("Test Task");

    @Test
    void testTaskTitleIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new TaskTitle(null);
        });
    }
}
