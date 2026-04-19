package com.bit.solutions.parking_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OccupancyResponseDTO {
    private Integer totalCapacity;
    private Integer availableSpaces;
    private Integer occupiedSpaces;
    private Double occupancyPercentage;
}