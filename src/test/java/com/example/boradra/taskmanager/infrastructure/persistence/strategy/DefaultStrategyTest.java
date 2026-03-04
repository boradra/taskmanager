package com.example.boradra.taskmanager.infrastructure.persistence.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DefaultStrategyTest {
    DefaultStrategy defaultStrategy = new DefaultStrategy();

    @Test
    void testCalculateNextExecutionDate() {
        LocalDate currentExecutionDate = LocalDate.of(2024, 6, 1);
        LocalDate nextExecutionDate = defaultStrategy.calculateNextExecutionDate(currentExecutionDate);
        assertEquals(currentExecutionDate, nextExecutionDate);
    }

    @Test
    void testGetTaskType() {
        assertEquals(com.example.boradra.taskmanager.domain.model.TaskType.ONCE, defaultStrategy.getTaskType());
    }
}
