package com.example.boradra.taskmanager.infrastructure.persistence.entity; 



import java.time.LocalDate;

import com.example.boradra.taskmanager.domain.model.TaskType;

import jakarta.persistence.*; 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "completed", nullable = false)
    private boolean completed;

    @Enumerated(EnumType.STRING)
    @Column(name = "tasktype")
    private TaskType taskType;

    @Column(name = "next_execution_date")
    private LocalDate nextExecutionDate;
}