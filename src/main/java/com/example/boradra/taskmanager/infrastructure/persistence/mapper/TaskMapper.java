package com.example.boradra.taskmanager.infrastructure.persistence.mapper;
import com.example.boradra.taskmanager.application.dto.TaskCreateRequest;
import com.example.boradra.taskmanager.application.dto.TaskResponse;
import com.example.boradra.taskmanager.application.dto.TaskUpdateRequest;
import com.example.boradra.taskmanager.domain.model.Task;
import com.example.boradra.taskmanager.infrastructure.persistence.entity.TaskJpaEntity;

import org.mapstruct.MappingTarget;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// I suspect this mapper creates a tight coupling between Application and Infrastructure.
// Currently exploring the best practice for splitting DTO-to-Domain and Domain-to-Entity mappings.

@Mapper(componentModel = "spring")
public interface TaskMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "completed", ignore = true)
    Task toDomain(TaskCreateRequest request);
    

    TaskJpaEntity toJpaEntity(Task domainTask);


    Task toDomain(TaskJpaEntity jpaEntity);


    TaskResponse toResponse(Task domainTask);

    
    @Mapping(target = "id", ignore = true)
    void updateDomainFromRequest(TaskUpdateRequest request, @MappingTarget Task task);
}
