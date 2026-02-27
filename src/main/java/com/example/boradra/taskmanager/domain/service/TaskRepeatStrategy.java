package com.example.boradra.taskmanager.domain.service;

import java.time.LocalDate;

import com.example.boradra.taskmanager.domain.model.TaskType;

public interface TaskRepeatStrategy {

    LocalDate calculateNextExecutionDate(LocalDate lastDate);

    TaskType getTaskType();
}
