package com.bit.solutions.parking_system.service.implement;

import com.bit.solutions.parking_system.entity.User;
import com.bit.solutions.parking_system.exceptions.BadRequestException;
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
        log.debug("Creating user: {}", user);
        validateUniqueUsername(user.getUsername());
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            log.warn("Attempted to create user without password");
            throw new BadRequestException("Password is required");
        }
        validatePasswordLength(user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        log.debug("Updating user with id={}", id);

        User existing = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found for update with id={}", id);
                    return new ResourceNotFoundException("User not found for update with id: " + id);
                });

        if (user.getUsername() != null && !user.getUsername().equals(existing.getUsername())) {
            validateUniqueUsername(user.getUsername());
            existing.setUsername(user.getUsername());
        }

        if (user.getName() != null) existing.setName(user.getName());
        if (user.getRole() != null) existing.setRole(user.getRole());
        if (user.getActive() != null) existing.setActive(user.getActive());

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            validatePasswordLength(user.getPassword());
            existing.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(existing);
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
            throw new BadRequestException("The username '" + username + "' is already taken");
        }
    }
    private void validatePasswordLength(String password) {
        if (password.length() < 6) {
            log.warn("Password too short");
            throw new BadRequestException("Password must be at least 6 characters long");
        }
}
}