package com.example.boradra.taskmanager.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.boradra.taskmanager.domain.model.TaskTitle;
import com.example.boradra.taskmanager.infrastructure.persistence.entity.TaskJpaEntity;
public interface JpaTaskRepository extends JpaRepository<TaskJpaEntity, Long> {

    boolean existsByTitle(TaskTitle taskTitle);
}

