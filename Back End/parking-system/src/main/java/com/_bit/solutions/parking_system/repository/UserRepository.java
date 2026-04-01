package com._bit.solutions.parking_system.repository;

import com._bit.solutions.parking_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
