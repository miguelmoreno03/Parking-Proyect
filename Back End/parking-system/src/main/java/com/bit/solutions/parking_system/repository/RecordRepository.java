package com.bit.solutions.parking_system.repository;
import com.bit.solutions.parking_system.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RecordRepository extends JpaRepository <Record,Long> {
    Optional<Record> findByTicketCode(String ticketCode);
}
