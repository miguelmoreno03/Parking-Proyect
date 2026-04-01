package com._bit.solutions.parking_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RecordRepository extends JpaRepository <Record,Long> {
    Optional<Record> findByTicketCode(String ticketCode);
}
