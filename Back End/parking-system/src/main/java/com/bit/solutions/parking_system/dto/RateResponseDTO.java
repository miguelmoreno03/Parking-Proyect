package com.bit.solutions.parking_system.dto;

import com.bit.solutions.parking_system.entity.enums.Type;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RateResponseDTO {
    private Long id;
    private Type type;
    private Double pricePerMinute;
}
