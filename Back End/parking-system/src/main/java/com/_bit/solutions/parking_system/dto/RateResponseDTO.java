package com._bit.solutions.parking_system.dto;

import com._bit.solutions.parking_system.entity.Enum.Type;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RateResponseDTO {
    private Long id;
    private Type type;
    private Double pricePerMinute;
}
