package com.example.todo.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserUpdateRequest {

    private Long id;
    private String name;
    private String lastname;
    private String email;
    private String username;
    private String password;
    private Set<String> roles;
}
