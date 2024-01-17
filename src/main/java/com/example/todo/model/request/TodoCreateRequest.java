package com.example.todo.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TodoCreateRequest {

    private String name;
    private List<String> categoryList;
    private String username;

}
