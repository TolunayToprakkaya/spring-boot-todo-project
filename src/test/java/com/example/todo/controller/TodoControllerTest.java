package com.example.todo.controller;

import com.example.todo.model.Todo;
import com.example.todo.model.TodoStatus;
import com.example.todo.model.request.TodoCreateRequest;
import com.example.todo.model.request.TodoUpdateRequest;
import com.example.todo.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TodoControllerTest {

    @Mock
    private TodoService todoService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private TodoController todoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllTodos_AdminRole() {
        List<Todo> todoList = new ArrayList<>();
        when(todoService.getAllTodos()).thenReturn(todoList);

        ResponseEntity<?> responseEntity = todoController.getAllTodos();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(todoList, responseEntity.getBody());

        verify(todoService, times(1)).getAllTodos();
    }

    @Test
    void testGetAllTodosByUserId() {
        String userId = "123";
        List<Todo> todoList = new ArrayList<>();
        when(todoService.getAllTodosByUserId(userId)).thenReturn(todoList);

        ResponseEntity<?> responseEntity = todoController.getAllTodosByUserId(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(todoList, responseEntity.getBody());

        verify(todoService, times(1)).getAllTodosByUserId(userId);
    }

    @Test
    void testSaveTodo() {
        TodoCreateRequest request = new TodoCreateRequest();
        when(todoService.saveTodo(authentication, request)).thenReturn(createTodo());

        ResponseEntity<?> responseEntity = todoController.saveTodo(authentication, request);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        verify(todoService, times(1)).saveTodo(authentication, request);
    }

    @Test
    void testUpdateTodo() {
        TodoUpdateRequest request = new TodoUpdateRequest();
        when(todoService.updateTodo(authentication, request)).thenReturn(createTodo());

        ResponseEntity<?> responseEntity = todoController.saveTodo(authentication, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(todoService, times(1)).updateTodo(authentication, request);
    }

    @Test
    void testDeleteTodo() {
        String todoId = "123";

        ResponseEntity<?> responseEntity = todoController.deleteTodo(todoId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(todoService, times(1)).deleteTodo(todoId);
    }

    private Todo createTodo() {
        return Todo.builder().id(123L)
                .name("Test")
                .categoryList(List.of("Work"))
                .todoStatus(TodoStatus.IN_PROGRESS)
                .userId("test")
                .build();
    }
}
