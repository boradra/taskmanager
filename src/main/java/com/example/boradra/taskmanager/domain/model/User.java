package com.example.boradra.taskmanager.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {

    private Long id;
    private String username;
    private String email;
    private String password;
}
