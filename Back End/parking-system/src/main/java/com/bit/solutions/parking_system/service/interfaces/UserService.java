package com.bit.solutions.parking_system.service.interfaces;

import com.bit.solutions.parking_system.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(Long id);

    User createUser(User user);

    User updateUser(Long id, User user);

    void deleteUser(Long id);
}
