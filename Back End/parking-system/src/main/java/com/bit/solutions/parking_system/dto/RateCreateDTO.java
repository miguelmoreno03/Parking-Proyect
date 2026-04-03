package com.bit.solutions.parking_system.dto;

import com.bit.solutions.parking_system.entity.enums.Type;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateCreateDTO {
    @NotNull(message = "El tipo es obligatorio")
    private Type type;
    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    private Double pricePerMinute;
}

