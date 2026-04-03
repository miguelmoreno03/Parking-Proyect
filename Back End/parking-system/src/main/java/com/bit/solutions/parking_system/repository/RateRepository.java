package com.bit.solutions.parking_system.repository;

import com.bit.solutions.parking_system.entity.enums.Type;
import com.bit.solutions.parking_system.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RateRepository extends JpaRepository <Rate,Long> {
    Optional<Rate> findByType(Type type);
    boolean existsByType(String type);
}
