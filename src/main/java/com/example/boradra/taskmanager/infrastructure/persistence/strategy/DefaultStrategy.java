package com.example.boradra.taskmanager.infrastructure.persistence.strategy;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.example.boradra.taskmanager.domain.model.TaskType;
import com.example.boradra.taskmanager.domain.service.TaskRepeatStrategy;
@Component
public class DefaultStrategy implements TaskRepeatStrategy {

    @Override
    public LocalDate calculateNextExecutionDate(LocalDate lastDate) {
        return lastDate; 
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.ONCE; 
    }

}
