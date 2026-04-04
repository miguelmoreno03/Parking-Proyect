package com.bit.solutions.parking_system.dto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
@Getter
@Builder
@JsonPropertyOrder({"id","totalCapacity","availableSpaces"})
public class ConfigurationResponseDTO {
    private Long id;
    private Integer totalCapacity;
    private Integer availableSpaces;
}
