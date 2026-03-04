package com.example.boradra.taskmanager.infrastructure.persistence.persistenceMapper;
import com.example.boradra.taskmanager.domain.model.Task;
import com.example.boradra.taskmanager.domain.model.TaskTitle;
import com.example.boradra.taskmanager.infrastructure.persistence.entity.TaskJpaEntity;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface PersistenceMapper {

    default String map(TaskTitle title) {
        return title != null ? title.getValue() : null;
    }

    default TaskTitle map(String title) {
        return title != null ? new TaskTitle(title) : null;

    }

    @Mapping(target = "title", source = "title")
    TaskJpaEntity toJpaEntity(Task domainTask);


    @Mapping(target = "title", source = "title")
    Task toDomain(TaskJpaEntity jpaEntity);

    
}
