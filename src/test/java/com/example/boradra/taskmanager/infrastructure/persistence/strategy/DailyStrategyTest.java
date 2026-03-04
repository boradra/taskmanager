package com.example.boradra.taskmanager.infrastructure.persistence.strategy;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class DailyStrategyTest {

    DailyStrategy dailyStrategy = new DailyStrategy();
    @Test
    void testCalculateNextExecutionDate() {
        LocalDate  currentExecutionDate = LocalDate.of(2024, 6, 1);
        LocalDate nextExecutionDate = dailyStrategy.calculateNextExecutionDate(currentExecutionDate);
        assertEquals(LocalDate.of(2024, 6, 2), nextExecutionDate);
    }

    @Test
    void testGetTaskType_ShouldReturnDaily() {
        assertEquals(com.example.boradra.taskmanager.domain.model.TaskType.DAILY, dailyStrategy.getTaskType());
    }
}
