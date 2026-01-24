package com.example.boradra.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.boradra.taskmanager.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
