package com.example.todo.service.impl;

import com.example.todo.model.User;
import com.example.todo.model.request.UserCreateRequest;
import com.example.todo.model.request.UserUpdateRequest;
import com.example.todo.repository.UserRepository;
import com.example.todo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByUserId(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User saveUser(UserCreateRequest userCreateRequest) {
        User user = User.builder()
                .id(generateUniqueId())
                .name(userCreateRequest.getName())
                .lastname(userCreateRequest.getLastname())
                .username(userCreateRequest.getUsername())
                .email(userCreateRequest.getEmail())
                .password(passwordEncoder.encode(userCreateRequest.getPassword()))
                .roles(userCreateRequest.getRoles())
                .createDate(LocalDateTime.now()).build();

        return userRepository.save(user);
    }

    @Override
    public User updateUser(UserUpdateRequest userUpdateRequest) {
        Optional<User> optionalUser = userRepository.findByUsername(userUpdateRequest.getUsername());

        User user = User.builder()
                .id(userUpdateRequest.getId())
                .name(userUpdateRequest.getName())
                .lastname(userUpdateRequest.getLastname())
                .email(userUpdateRequest.getEmail())
                .username(userUpdateRequest.getUsername())
                .password(passwordEncoder.encode(userUpdateRequest.getPassword()))
                .roles(userUpdateRequest.getRoles())
                .createDate(optionalUser.map(User::getCreateDate).orElse(null))
                .updateDate(LocalDateTime.now()).build();

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    private Long generateUniqueId() {
        long val = -1;
        do {
            val = UUID.randomUUID().getMostSignificantBits();
        } while (val < 0);
        return val;
    }
}
