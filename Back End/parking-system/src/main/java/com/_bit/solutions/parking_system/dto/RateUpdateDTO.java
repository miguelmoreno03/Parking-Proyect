package com._bit.solutions.parking_system.dto;

import com._bit.solutions.parking_system.entity.Enum.Type;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateUpdateDTO {
    private Type type;
    @Positive(message = "El precio debe ser mayor a 0")
    private Double pricePerMinute;
}
