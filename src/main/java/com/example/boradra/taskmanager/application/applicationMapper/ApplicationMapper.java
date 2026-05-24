package com.example.boradra.taskmanager.application.applicationMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import com.example.boradra.taskmanager.application.dto.TaskCreateRequest;
import com.example.boradra.taskmanager.application.dto.TaskResponse;
import com.example.boradra.taskmanager.domain.model.Task;
import com.example.boradra.taskmanager.domain.model.TaskTitle;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "completed", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "nextExecutionDate", ignore = true)
    Task toDomain(TaskCreateRequest request);

    @Mapping(source = "title", target = "title")
    TaskResponse toResponse(Task task);

    default String map(TaskTitle title) {
        return title != null ? title.getValue() : null;
    }

    default TaskTitle map(String title) {
        return title != null ? new TaskTitle(title) : null;
    }

    List<Task> toDomainList(List<TaskCreateRequest> requests);

    List<TaskResponse> toResponseList(List<Task> tasks);
    

}
