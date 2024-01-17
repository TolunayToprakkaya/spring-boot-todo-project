package com.example.todo.service.impl;

import com.example.todo.model.Todo;
import com.example.todo.model.TodoStatus;
import com.example.todo.model.User;
import com.example.todo.model.request.TodoCreateRequest;
import com.example.todo.model.request.TodoUpdateRequest;
import com.example.todo.repository.TodoRepository;
import com.example.todo.repository.UserRepository;
import com.example.todo.service.TodoService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoServiceImpl(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    public List<Todo> getAllTodosByUserId(String userId) {
        return todoRepository.findAllByUserId(userId);
    }

    @Override
    public Todo saveTodo(Authentication authentication, TodoCreateRequest todoCreateRequest) {
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);

        Todo todo = Todo.builder()
                .id(generateUniqueId())
                .name(todoCreateRequest.getName())
                .categoryList(todoCreateRequest.getCategoryList())
                .todoStatus(TodoStatus.IN_PROGRESS)
                .userId(userOptional.map(user -> String.valueOf(user.getId())).orElse(null))
                .createDate(LocalDateTime.now()).build();

        return todoRepository.save(todo);
    }

    @Override
    public Todo updateTodo(Authentication authentication, TodoUpdateRequest todoUpdateRequest) {
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        Optional<Todo> todoOptional = todoRepository.findById(String.valueOf(todoUpdateRequest.getId()));

        Todo todo = Todo.builder()
                .id(todoUpdateRequest.getId())
                .name(todoUpdateRequest.getName())
                .categoryList(todoUpdateRequest.getCategoryList())
                .todoStatus(todoUpdateRequest.getTodoStatus())
                .userId(userOptional.map(user -> String.valueOf(user.getId())).orElse(null))
                .createDate(todoOptional.map(Todo::getCreateDate).orElse(null))
                .updateDate(LocalDateTime.now()).build();

        return todoRepository.save(todo);
    }

    @Override
    public void deleteTodo(String todoId) {
        todoRepository.deleteById(todoId);
    }

    private Long generateUniqueId() {
        long val = -1;
        do {
            val = UUID.randomUUID().getMostSignificantBits();
        } while (val < 0);
        return val;
    }
}
