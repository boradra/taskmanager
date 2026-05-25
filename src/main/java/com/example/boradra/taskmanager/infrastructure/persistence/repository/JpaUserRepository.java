package com.example.boradra.taskmanager.infrastructure.persistence.repository;

import com.example.boradra.taskmanager.infrastructure.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserJpaEntity, Long> {

    Optional<UserJpaEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
