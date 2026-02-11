package com.example.boradra.taskmanager.taskMapper;
import com.example.boradra.taskmanager.dto.TaskCreateRequest;
import com.example.boradra.taskmanager.entity.Task;
import com.example.boradra.taskmanager.dto.TaskResponse;
import com.example.boradra.taskmanager.dto.TaskUpdateRequest;
import org.mapstruct.MappingTarget;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "completed", ignore = true)
    Task toTask(TaskCreateRequest taskCreateRequest);

    TaskResponse toTaskResponse(Task task);

    @Mapping(target = "id", ignore = true)
    void updateTaskFromRequest(TaskUpdateRequest taskUpdateRequest, @MappingTarget Task task);

}
