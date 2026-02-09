package com.example.boradra.taskmanager.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.boradra.taskmanager.entity.Task;
public interface TaskRepository extends JpaRepository<Task, Long> {
}

