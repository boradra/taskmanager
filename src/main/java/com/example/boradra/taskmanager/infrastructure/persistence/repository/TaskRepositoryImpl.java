package com.example.boradra.taskmanager.infrastructure.persistence.repository;

import com.example.boradra.taskmanager.domain.model.Task;
import com.example.boradra.taskmanager.domain.repository.TaskRepository;
import com.example.boradra.taskmanager.infrastructure.persistence.entity.TaskJpaEntity;
import com.example.boradra.taskmanager.infrastructure.persistence.persistenceMapper.PersistenceMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository 
@RequiredArgsConstructor 
public class TaskRepositoryImpl implements TaskRepository {

    private final JpaTaskRepository jpaTaskRepository; 
    private final PersistenceMapper taskMapper; 

    @Override
    public Task save(Task task) {

        TaskJpaEntity entity = taskMapper.toJpaEntity(task);
        

        TaskJpaEntity savedEntity = jpaTaskRepository.save(entity);
        

        return taskMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return jpaTaskRepository.findById(id)
                .map(taskMapper::toDomain); 
    }

    @Override
    public List<Task> findAll() {
        return jpaTaskRepository.findAll().stream()
                .map(taskMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaTaskRepository.deleteById(id);
    }
}