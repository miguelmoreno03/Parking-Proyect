package com.bit.solutions.parking_system.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfigurationCreateDTO {
    @NotNull(message = "Total capacity is required")
    @Positive(message = "Total capacity must be greater than 0")
    private Integer totalCapacity;
}
