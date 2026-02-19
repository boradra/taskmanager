package com.example.boradra.taskmanager.infrastructure.persistence.persistenceMapper;
import com.example.boradra.taskmanager.domain.model.Task;
import com.example.boradra.taskmanager.infrastructure.persistence.entity.TaskJpaEntity;


import org.mapstruct.Mapper;


// I suspect this mapper creates a tight coupling between Application and Infrastructure.
// Currently exploring the best practice for splitting DTO-to-Domain and Domain-to-Entity mappings.

@Mapper(componentModel = "spring")
public interface PersistenceMapper {

    TaskJpaEntity toJpaEntity(Task domainTask);


    Task toDomain(TaskJpaEntity jpaEntity);

    
   
}
