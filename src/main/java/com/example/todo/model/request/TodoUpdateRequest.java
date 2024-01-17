package com.example.todo.model.request;

import com.example.todo.model.TodoStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TodoUpdateRequest {

    private Long id;
    private String name;
    private List<String> categoryList;
    private TodoStatus todoStatus;
    private String userId;
}
