package com.bit.solutions.parking_system.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfigurationUpdateDTO {
    @Positive(message = "Total capacity must be greater than 0")
    private Integer totalCapacity;
}
