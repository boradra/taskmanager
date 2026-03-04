package com.example.boradra.taskmanager.infrastructure.persistence.strategy;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeeklyStrategyTest {

    private WeeklyStrategy weeklyStrategy = new WeeklyStrategy();
    

    @Test
    public void testCalculateNextExecutionDate() {     
        LocalDate currentExecutionDate = LocalDate.of(2024, 6, 1); 

    
        LocalDate nextExecutionDate = weeklyStrategy.calculateNextExecutionDate(currentExecutionDate);

       
        assertEquals(LocalDate.of(2024, 6, 8), nextExecutionDate); 
    }

    @Test
    void getTaskType_ShouldReturnWeekly() {
        assertEquals(com.example.boradra.taskmanager.domain.model.TaskType.WEEKLY, weeklyStrategy.getTaskType());
    }

    @Test
    void testCalculateNextExecutionDate2() {
        
    }

    @Test
    void testGetTaskType() {
        
    }
}
