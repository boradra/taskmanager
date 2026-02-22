package com.example.boradra.taskmanager.application.applicationMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.boradra.taskmanager.application.dto.TaskCreateRequest;
import com.example.boradra.taskmanager.application.dto.TaskResponse;
import com.example.boradra.taskmanager.application.dto.TaskUpdateRequest;
import com.example.boradra.taskmanager.domain.model.Task;
import com.example.boradra.taskmanager.domain.model.TaskTitle;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    default String map(TaskTitle title) {
        return title != null ? title.getValue() : null;
    }

    default TaskTitle map(String title) {
        return title != null ? new TaskTitle(title) : null;

    }
    
    @Mapping(target = "id" , ignore = true )
    @Mapping(target = "completed", ignore = true)
    Task toDomain(TaskCreateRequest request);

    @Mapping(source = "title", target = "title")
    TaskResponse toResponse(Task task);

     @Mapping(target = "id", ignore = true)
    void updateDomainFromRequest(TaskUpdateRequest request, @MappingTarget Task task);
    
}
