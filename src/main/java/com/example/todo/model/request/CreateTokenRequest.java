package com.example.todo.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTokenRequest {

    private String username;
    private String password;
}
