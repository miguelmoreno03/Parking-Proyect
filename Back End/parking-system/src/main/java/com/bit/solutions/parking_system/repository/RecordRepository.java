package com.bit.solutions.parking_system.repository;
import com.bit.solutions.parking_system.entity.Record;
import com.bit.solutions.parking_system.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RecordRepository extends JpaRepository <Record,Long> {
    Optional<Record> findByTicketCode(String ticketCode);
    List<Record> findByStatus(Status status);
    boolean existsByPlateAndStatus(String plate, Status status);
    long countByStatus(Status status);
}
