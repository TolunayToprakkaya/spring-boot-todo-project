package com.example.todo.service;

import com.example.todo.model.Todo;
import com.example.todo.model.TodoStatus;
import com.example.todo.model.User;
import com.example.todo.model.request.TodoCreateRequest;
import com.example.todo.model.request.TodoUpdateRequest;
import com.example.todo.repository.TodoRepository;
import com.example.todo.repository.UserRepository;
import com.example.todo.service.impl.TodoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TodoServiceImplTest {

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private TodoServiceImpl todoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllTodos() {
        List<Todo> todoList = new ArrayList<>();
        when(todoRepository.findAll()).thenReturn(todoList);

        List<Todo> result = todoService.getAllTodos();

        assertEquals(todoList, result);
        verify(todoRepository, times(1)).findAll();
    }

    @Test
    void testGetAllTodosByUserId() {
        String userId = "123";
        List<Todo> todoList = new ArrayList<>();
        when(todoRepository.findAllByUserId(userId)).thenReturn(todoList);

        List<Todo> result = todoService.getAllTodosByUserId(userId);

        assertEquals(todoList, result);
        verify(todoRepository, times(1)).findAllByUserId(userId);
    }

    @Test
    void testSaveTodo() {
        String username = "john.doe";
        when(authentication.getName()).thenReturn(username);

        User user = createUser();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        TodoCreateRequest request = new TodoCreateRequest();
        request.setName("Buy groceries");
        request.setCategoryList(List.of("Shopping"));

        when(todoRepository.save(any(Todo.class))).thenAnswer(invocation -> {
            Todo savedTodo = invocation.getArgument(0);
            savedTodo.setId(123L); // Mocked ID
            return savedTodo;
        });

        Todo savedTodo = todoService.saveTodo(authentication, request);

        assertNotNull(savedTodo);
        assertEquals("Buy groceries", savedTodo.getName());
        assertEquals(List.of("Shopping"), savedTodo.getCategoryList());
        assertEquals(TodoStatus.IN_PROGRESS, savedTodo.getTodoStatus());
        assertEquals("123", savedTodo.getUserId()); // Mocked user ID
        assertNotNull(savedTodo.getCreateDate());

        verify(userRepository, times(1)).findByUsername(username);
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    void testUpdateTodo() {
        String username = "john.doe";
        when(authentication.getName()).thenReturn(username);

        User user = createUser();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        TodoUpdateRequest request = new TodoUpdateRequest();
        request.setName("Buy groceries");
        request.setCategoryList(List.of("Shopping"));
        request.setTodoStatus(TodoStatus.DONE);

        when(todoRepository.save(any(Todo.class))).thenAnswer(invocation -> {
            Todo updatedTodo = invocation.getArgument(0);
            updatedTodo.setId(123L); // Mocked ID
            return updatedTodo;
        });

        Todo updatedTodo = todoService.updateTodo(authentication, request);

        assertNotNull(updatedTodo);
        assertEquals("Buy groceries", updatedTodo.getName());
        assertEquals(List.of("Shopping"), updatedTodo.getCategoryList());
        assertEquals(TodoStatus.DONE, updatedTodo.getTodoStatus());
        assertEquals("123", updatedTodo.getUserId()); // Mocked user ID

        verify(userRepository, times(1)).findByUsername(username);
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    void testDeleteTodo() {
        String todoId = "123";

        todoService.deleteTodo(todoId);

        verify(todoRepository, times(1)).deleteById(todoId);
    }

    private User createUser() {
        return User.builder().id(123L)
                .name("Test")
                .lastname("Test")
                .email("test@gmail.com")
                .username("test")
                .password("test123")
                .roles(Set.of("USER"))
                .build();
    }
}
