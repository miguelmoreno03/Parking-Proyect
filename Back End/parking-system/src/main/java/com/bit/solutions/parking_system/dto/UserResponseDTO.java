package com.bit.solutions.parking_system.dto;

import com.bit.solutions.parking_system.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
@JsonPropertyOrder({
        "id",
        "name",
        "username",
        "role",
        "active",
        "createdAt"
})
public class UserResponseDTO {
    private Long id;
    private String name;
    private String username;
    private Role role;
    private Boolean active;
    private LocalDateTime createdAt;
}
