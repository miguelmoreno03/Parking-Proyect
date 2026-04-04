package com.bit.solutions.parking_system.dto;

import com.bit.solutions.parking_system.entity.enums.Type;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonPropertyOrder({"id","type","pricePerMinute"})
public class RateResponseDTO {
    private Long id;
    private Type type;
    private Double pricePerMinute;
}
