package com.bit.solutions.parking_system.service.implement;

import com.bit.solutions.parking_system.entity.User;
import com.bit.solutions.parking_system.exceptions.BadRequestException;
import com.bit.solutions.parking_system.exceptions.ConflictException;
import com.bit.solutions.parking_system.exceptions.ResourceNotFoundException;
import com.bit.solutions.parking_system.repository.UserRepository;
import com.bit.solutions.parking_system.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        log.debug("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        log.debug("Fetching user by id={}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found with id={}", id);
                    return new ResourceNotFoundException("User not found with id " + id);
                });
    }

    @Override
    public User createUser(User user) {
        if (user == null) {
            throw new BadRequestException("User body is required");
        }

        normalizeUser(user);

        log.debug("Creating user with username={}", user.getUsername());

        validateRequiredFieldsForCreate(user);
        validateUniqueUsername(user.getUsername());
        validatePasswordLength(user.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User saved = userRepository.save(user);
        log.info("User created successfully with id={} and username={}", saved.getId(), saved.getUsername());

        return saved;
    }

    @Override
    public User updateUser(Long id, User user) {
        if (user == null) {
            throw new BadRequestException("User body is required");
        }

        log.debug("Updating user with id={}", id);

        User existing = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found for update with id={}", id);
                    return new ResourceNotFoundException("User not found for update with id: " + id);
                });

        normalizeUser(user);

        if (user.getUsername() != null && !user.getUsername().equals(existing.getUsername())) {
            validateUniqueUsername(user.getUsername());
            existing.setUsername(user.getUsername());
        }

        if (user.getName() != null) {
            existing.setName(user.getName());
        }

        if (user.getRole() != null) {
            existing.setRole(user.getRole());
        }

        if (user.getActive() != null) {
            existing.setActive(user.getActive());
        }

        if (user.getPassword() != null) {
            validatePasswordLength(user.getPassword());
            existing.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        User updated = userRepository.save(existing);
        log.info("User updated successfully with id={}", updated.getId());

        return updated;
    }

    @Override
    public void deleteUser(Long id) {
        log.debug("Deleting user with id={}", id);

        User existing = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found for deletion with id={}", id);
                    return new ResourceNotFoundException("User not found with id: " + id);
                });

        userRepository.delete(existing);
        log.info("User deleted successfully with id={}", id);
    }

    private void validateUniqueUsername(String username) {
        if (username != null && userRepository.existsByUsername(username)) {
            log.warn("Username already exists: {}", username);
            throw new ConflictException("The username '" + username + "' is already taken");
        }
    }

    private void validatePasswordLength(String password) {
        if (password == null || password.isBlank()) {
            log.warn("Password is missing");
            throw new BadRequestException("Password is required");
        }

        if (password.length() < 6) {
            log.warn("Password too short");
            throw new BadRequestException("Password must be at least 6 characters long");
        }
    }

    private void validateRequiredFieldsForCreate(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            throw new BadRequestException("Name is required");
        }

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new BadRequestException("Username is required");
        }

        if (user.getRole() == null) {
            throw new BadRequestException("Role is required");
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new BadRequestException("Password is required");
        }
    }

    private void normalizeUser(User user) {
        if (user.getName() != null) {
            user.setName(user.getName().trim());
        }

        if (user.getUsername() != null) {
            user.setUsername(user.getUsername().trim());
        }

        if (user.getPassword() != null) {
            user.setPassword(user.getPassword().trim());
        }
    }
}