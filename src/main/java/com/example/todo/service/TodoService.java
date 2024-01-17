package com.example.todo.service;

import com.example.todo.model.Todo;
import com.example.todo.model.request.TodoCreateRequest;
import com.example.todo.model.request.TodoUpdateRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TodoService {

    List<Todo> getAllTodos();

    List<Todo> getAllTodosByUserId(String userId);

    Todo saveTodo(Authentication authentication, TodoCreateRequest todoCreateRequest);

    Todo updateTodo(Authentication authentication, TodoUpdateRequest todoUpdateRequest);

    void deleteTodo(String todoId);
}
