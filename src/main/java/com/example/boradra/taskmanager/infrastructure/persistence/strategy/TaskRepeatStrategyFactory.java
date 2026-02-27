package com.example.boradra.taskmanager.infrastructure.persistence.strategy;
import java.util.Map;
import com.example.boradra.taskmanager.domain.model.TaskType;
import com.example.boradra.taskmanager.domain.service.TaskRepeatStrategy;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class TaskRepeatStrategyFactory {

    private final Map<TaskType, TaskRepeatStrategy> strategies;

    public TaskRepeatStrategyFactory(List<TaskRepeatStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(TaskRepeatStrategy::getTaskType, strategy -> strategy));
        
    }

    public TaskRepeatStrategy getStrategy(TaskType taskType) {
        TaskRepeatStrategy strategy = strategies.get(taskType);
        return (strategy != null) ? strategy : strategies.get(TaskType.ONCE);
    }   
}
