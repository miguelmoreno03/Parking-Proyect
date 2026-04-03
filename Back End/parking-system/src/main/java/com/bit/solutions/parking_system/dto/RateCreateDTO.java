package com.bit.solutions.parking_system.dto;

import com.bit.solutions.parking_system.entity.enums.Type;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateCreateDTO {
    @NotNull(message = "Type is required")
    private Type type;
    @NotNull(message = "Price is required")
    @Positive(message = "The price must be greater than 0")
    private Double pricePerMinute;
}

