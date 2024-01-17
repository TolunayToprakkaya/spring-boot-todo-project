package com.example.todo.service;

import com.example.todo.model.User;
import com.example.todo.model.request.UserCreateRequest;
import com.example.todo.model.request.UserUpdateRequest;
import com.example.todo.repository.UserRepository;
import com.example.todo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindAllUsers() {
        List<User> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.findAllUsers();

        assertEquals(userList, result);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserByUserId() {
        String userId = "123";
        User user = createUser();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserByUserId(userId);

        assertEquals(Optional.of(user), result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testSaveUser() {
        UserCreateRequest request = new UserCreateRequest();
        request.setName("John");
        request.setLastname("Doe");
        request.setUsername("john.doe");
        request.setEmail("john.doe@example.com");
        request.setPassword("password");
        request.setRoles(Set.of("USER"));

        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

        when(userRepository.save(any(User.class))).thenReturn(createUser(generateUniqueId(), request.getName(), request.getLastname(), request.getEmail(), request.getUsername(), request.getPassword(), request.getRoles()));

        User savedUser = userService.saveUser(request);

        assertNotNull(savedUser);
        assertEquals("John", savedUser.getName());
        assertEquals("Doe", savedUser.getLastname());
        assertEquals("john.doe", savedUser.getUsername());
        assertEquals("john.doe@example.com", savedUser.getEmail());
        assertNotNull(savedUser.getPassword());
        assertEquals(Set.of("USER"), savedUser.getRoles());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUser() {
        UserUpdateRequest request = new UserUpdateRequest();
        request.setId(123L);
        request.setName("UpdatedName");
        request.setLastname("UpdatedLastname");
        request.setUsername("updated.username");
        request.setEmail("updated.email@example.com");
        request.setPassword("updatedPassword");
        request.setRoles(Set.of("ADMIN"));

        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedUpdatedPassword");
        when(userRepository.save(any(User.class))).thenReturn(createUser(generateUniqueId(), request.getName(), request.getLastname(), request.getEmail(), request.getUsername(), request.getPassword(), request.getRoles()));

        User updatedUser = userService.updateUser(request);

        assertNotNull(updatedUser);
        assertNotNull(updatedUser.getId());
        assertEquals("UpdatedName", updatedUser.getName());
        assertEquals("UpdatedLastname", updatedUser.getLastname());
        assertEquals("updated.username", updatedUser.getUsername());
        assertEquals("updated.email@example.com", updatedUser.getEmail());
        assertNotNull(updatedUser.getPassword());
        assertEquals(Set.of("ADMIN"), updatedUser.getRoles());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        String userId = "123";

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    private Long generateUniqueId() {
        long val = -1;
        do {
            val = UUID.randomUUID().getMostSignificantBits();
        } while (val < 0);
        return val;
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

    private User createUser(Long id, String name, String lastname, String email, String username, String password, Set<String> roles) {
        return User.builder().id(id)
                .name(name)
                .lastname(lastname)
                .email(email)
                .username(username)
                .password(password)
                .roles(roles)
                .build();
    }
}
