package com.bit.solutions.parking_system.mappers;
import com.bit.solutions.parking_system.dto.RecordResponseDTO;
import com.bit.solutions.parking_system.dto.RecordUpdateDTO;
import com.bit.solutions.parking_system.entity.Record;
import com.bit.solutions.parking_system.dto.RecordCreateDTO;
import com.bit.solutions.parking_system.entity.enums.Status;

import java.time.LocalDateTime;

public final class RecordMapper {
    private RecordMapper(){}
    public static Record toEntity(RecordCreateDTO dto) {
        if (dto == null) return null;

        return Record.builder()
                .plate(dto.getPlate().toUpperCase().trim())
                .entryTime(LocalDateTime.now())
                .status(Status.ACTIVE)
                .notificationMethod(dto.getNotificationMethod())
                .notificationTarget(dto.getNotificationTarget())
                .build();
    }

    // UPDATE
    public static void updateEntity(Record existing, RecordUpdateDTO dto) {
        if (existing == null || dto == null) return;

        if (dto.getPlate() != null) {
            existing.setPlate(dto.getPlate().toUpperCase().trim());
        }
    }

    // RESPONSE
    public static RecordResponseDTO toDTO(Record entity) {
        if (entity == null) return null;

        return RecordResponseDTO.builder()
                .id(entity.getId())
                .ticketCode(entity.getTicketCode())
                .plate(entity.getPlate())
                .entryTime(entity.getEntryTime())
                .exitTime(entity.getExitTime())
                .totalMinutes(entity.getTotalMinutes())
                .amountPaid(entity.getAmountPaid())
                .status(entity.getStatus())
                .type(entity.getRate() != null ? entity.getRate().getType().name() : null)
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .notificationMethod(entity.getNotificationMethod())
                .notificationTarget(entity.getNotificationTarget())
                .build();
    }
}
