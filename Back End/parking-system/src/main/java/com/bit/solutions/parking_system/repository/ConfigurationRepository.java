package com.bit.solutions.parking_system.repository;

import com.bit.solutions.parking_system.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration,Long> {
}
