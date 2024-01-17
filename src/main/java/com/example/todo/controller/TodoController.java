package com.example.todo.controller;

import com.example.todo.model.request.TodoCreateRequest;
import com.example.todo.model.request.TodoUpdateRequest;
import com.example.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllTodos() {
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllTodosByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(todoService.getAllTodosByUserId(userId));
    }

    @PostMapping("/createTodo")
    public ResponseEntity<?> saveTodo(Authentication authentication, @RequestBody TodoCreateRequest request) {
        return new ResponseEntity<>(todoService.saveTodo(authentication, request), HttpStatus.CREATED);
    }

    @PutMapping("/updateTodo")
    public ResponseEntity<?> saveTodo(Authentication authentication, @RequestBody TodoUpdateRequest request) {
        return new ResponseEntity<>(todoService.updateTodo(authentication, request), HttpStatus.OK);
    }

    @DeleteMapping("/deleteTodo/{todoId}")
    public ResponseEntity<?> deleteTodo(@PathVariable String todoId) {
        todoService.deleteTodo(todoId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
