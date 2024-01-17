package com.example.todo.service;

import com.example.todo.model.User;
import com.example.todo.model.request.UserCreateRequest;
import com.example.todo.model.request.UserUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAllUsers();

    Optional<User> getUserByUserId(String userId);

    User saveUser(UserCreateRequest userCreateRequest);

    User updateUser(UserUpdateRequest userUpdateRequest);

    void deleteUser(String userId);
}
