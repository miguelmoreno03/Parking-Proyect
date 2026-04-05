package com.bit.solutions.parking_system.dto;

import com.bit.solutions.parking_system.entity.enums.Status;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonPropertyOrder({
        "id",
        "ticketCode",
        "plate",
        "entryTime",
        "exitTime",
        "totalMinutes",
        "amountPaid",
        "status",
        "type",
        "userId"
})
public class RecordResponseDTO {
    private Long id;
    private String ticketCode;
    private String plate;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private Long totalMinutes;
    private Double amountPaid;
    private Status status;
    private String type;
    private Long userId;
}
