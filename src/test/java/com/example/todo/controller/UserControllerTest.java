package com.example.todo.controller;

import com.example.todo.model.User;
import com.example.todo.model.request.CreateTokenRequest;
import com.example.todo.model.request.UserCreateRequest;
import com.example.todo.model.request.UserUpdateRequest;
import com.example.todo.security.JwtUtil;
import com.example.todo.security.model.ResponseToken;
import com.example.todo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllUsers_AdminRole() {
        when(userService.findAllUsers()).thenReturn(List.of(createUser(), createUser()));

        ResponseEntity<?> responseEntity = userController.getAllUsers();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, ((List<User>) responseEntity.getBody()).size());

        verify(userService, times(1)).findAllUsers();
    }

    @Test
    void testGetUserByUserId() {
        String userId = "123";
        when(userService.getUserByUserId(userId)).thenReturn(Optional.of(createUser()));

        ResponseEntity<?> responseEntity = userController.getUserByUserId(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        verify(userService, times(1)).getUserByUserId(userId);
    }

    @Test
    void testRegister_DefaultRoles() {
        UserCreateRequest request = new UserCreateRequest();
        when(userService.saveUser(any(UserCreateRequest.class))).thenReturn(createUser());

        ResponseEntity<?> responseEntity = userController.register(request);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        verify(userService, times(1)).saveUser(any(UserCreateRequest.class));
    }

    @Test
    void testToken_InvalidCredentials() {
        CreateTokenRequest request = new CreateTokenRequest();
        request.setUsername("john.doe");
        request.setPassword("wrongPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new RuntimeException("Invalid credentials"));

        ResponseToken responseToken = userController.token(request);

        assertNotNull(responseToken);

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void testUpdateUser() {
        UserUpdateRequest request = new UserUpdateRequest();
        when(userService.updateUser(request)).thenReturn(createUser());

        ResponseEntity<?> responseEntity = userController.updateUser(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        verify(userService, times(1)).updateUser(request);
    }

    @Test
    void testDeleteUser_AdminRole() {
        String userId = "123";

        ResponseEntity<?> responseEntity = userController.deleteUser(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(userService, times(1)).deleteUser(userId);
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
