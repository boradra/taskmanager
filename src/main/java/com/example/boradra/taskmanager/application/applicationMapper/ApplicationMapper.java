package com.example.boradra.taskmanager.application.applicationMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.boradra.taskmanager.application.dto.TaskCreateRequest;
import com.example.boradra.taskmanager.application.dto.TaskResponse;
import com.example.boradra.taskmanager.application.dto.TaskUpdateRequest;
import com.example.boradra.taskmanager.domain.model.Task;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    @Mapping(target = "id" , ignore = true )
    @Mapping(target = "completed", ignore = true)
    Task toDomain(TaskCreateRequest request);

    TaskResponse toResponse(Task task);

     @Mapping(target = "id", ignore = true)
    void updateDomainFromRequest(TaskUpdateRequest request, @MappingTarget Task task);
    
}
