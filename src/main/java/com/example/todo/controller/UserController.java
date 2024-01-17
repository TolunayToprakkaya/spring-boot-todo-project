package com.example.todo.controller;

import com.example.todo.model.request.CreateTokenRequest;
import com.example.todo.model.request.UserCreateRequest;
import com.example.todo.model.request.UserUpdateRequest;
import com.example.todo.security.JwtUtil;
import com.example.todo.security.model.ResponseToken;
import com.example.todo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getUserByUserId(userId));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserCreateRequest request) {
        if (request.getRoles() == null) {
            request.setRoles(Set.of("USER"));
        }
        return new ResponseEntity<>(userService.saveUser(request), HttpStatus.CREATED);
    }

    @PostMapping("/token")
    public ResponseToken token(@RequestBody CreateTokenRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            return ResponseToken.ok(
                    JwtUtil.generateAccessToken(request.getUsername()),
                    JwtUtil.generateRefreshToken(request.getUsername())
            );
        } catch (Exception exception) {
            return ResponseToken.error(exception.getMessage());
        }
    }

    @GetMapping("/refreshToken")
    public ResponseToken accessTokenFromRefreshToken(@RequestParam String refreshToken) {
        try {
            String username = JwtUtil.getUsernameFromRefreshToken(refreshToken);
            return ResponseToken.ok(
                    JwtUtil.generateAccessToken(username),
                    refreshToken
            );
        } catch (Exception exception) {
            return ResponseToken.error(exception.getMessage());
        }
    }

    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateRequest request) {
        return new ResponseEntity<>(userService.updateUser(request), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
