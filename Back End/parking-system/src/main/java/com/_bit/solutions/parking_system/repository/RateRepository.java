package com._bit.solutions.parking_system.repository;

import com._bit.solutions.parking_system.entity.Enum.Type;
import com._bit.solutions.parking_system.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RateRepository extends JpaRepository <Rate,Long> {
    Optional<Rate> findByType(Type type);
    boolean existingByType(Type type);
}
