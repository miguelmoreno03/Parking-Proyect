package com.bit.solutions.parking_system.controller;

import com.bit.solutions.parking_system.dto.UserCreateDTO;
import com.bit.solutions.parking_system.dto.UserResponseDTO;
import com.bit.solutions.parking_system.dto.UserUpdateDTO;
import com.bit.solutions.parking_system.entity.User;
import com.bit.solutions.parking_system.mappers.UserMapper;
import com.bit.solutions.parking_system.service.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        List<User> users = userService.getAllUsers();
        List<UserResponseDTO> response = new ArrayList<>();
        for (User user : users) {
            response.add(UserMapper.toDTO(user));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserCreateDTO dto) {
        User user = UserMapper.toEntity(dto);
        User saved = userService.createUser(user);
        return ResponseEntity
                .created(URI.create("/users/" + saved.getId()))
                .body(UserMapper.toDTO(saved));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setActive(dto.getActive());

        User updated = userService.updateUser(id, user);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(UserMapper.toDTO(updated));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User with id " + id + " was successfully deleted");
    }
}


