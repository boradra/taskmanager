package com.example.boradra.taskmanager.infrastructure.persistence.strategy;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.example.boradra.taskmanager.domain.model.TaskType;
import com.example.boradra.taskmanager.domain.service.TaskRepeatStrategy;
@Component
public class WeeklyStrategy implements TaskRepeatStrategy {

    @Override
    public LocalDate calculateNextExecutionDate(LocalDate currentExecutionDate) {
        return currentExecutionDate.plusWeeks(1);
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.WEEKLY;
    }

}
