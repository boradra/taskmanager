package com.example.boradra.taskmanager.infrastructure.persistence.repository;

import com.example.boradra.taskmanager.domain.model.User;
import com.example.boradra.taskmanager.domain.repository.UserRepository;
import com.example.boradra.taskmanager.infrastructure.persistence.entity.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public User save(User user) {
        UserJpaEntity entity = toEntity(user);
        UserJpaEntity saved = jpaUserRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    private UserJpaEntity toEntity(User user) {
        return new UserJpaEntity(user.getId(), user.getUsername(), user.getEmail(), user.getPassword());
    }

    private User toDomain(UserJpaEntity entity) {
        return User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .build();
    }
}
