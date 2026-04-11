package com.bit.solutions.parking_system.dto;

import com.bit.solutions.parking_system.entity.enums.Type;
import com.bit.solutions.parking_system.notification.enums.NotificationMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordCreateDTO {
    @NotBlank(message = "Plate is required")
    private String plate;
    @NotNull(message = "User id is required")
    private Long userId;
    @NotNull(message = "Type is required")
    private Type type;
    @NotNull(message = "Type is required")
    private NotificationMethod notificationMethod;
    @NotNull(message = "Type is required")
    private String notificationTarget;
}
