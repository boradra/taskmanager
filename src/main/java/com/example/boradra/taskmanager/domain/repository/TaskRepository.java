package com.example.boradra.taskmanager.domain.repository;
import java.util.List;
import java.util.Optional;
import com.example.boradra.taskmanager.domain.model.Task;

public interface TaskRepository {

    Task save(Task task);
    
    Optional<Task> findById(Long id);
    
    List<Task> findAll();
    
    void deleteById(Long id);
}
