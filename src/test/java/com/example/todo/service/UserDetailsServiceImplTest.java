package com.example.todo.service;

import com.example.todo.model.User;
import com.example.todo.repository.UserRepository;
import com.example.todo.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testLoadUserByUsername() {
        User user = createUser();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals("test123", userDetails.getPassword());
        assertNotNull(userDetails.getAuthorities());

        verify(userRepository, times(1)).findByUsername(user.getUsername());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        String username = "nonexistent.user";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(username),
                "username: " + username + " not found"
        );

        verify(userRepository, times(1)).findByUsername(username);
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
