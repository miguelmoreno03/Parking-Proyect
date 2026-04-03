package com.bit.solutions.parking_system.dto;

import com.bit.solutions.parking_system.entity.enums.Type;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateUpdateDTO {
    private Type type;
    @Positive(message = "The price must be greater than 0")
    private Double pricePerMinute;
}
