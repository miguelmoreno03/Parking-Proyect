package com._bit.solutions.parking_system.mappers;
import com._bit.solutions.parking_system.dto.RateCreateDTO;
import com._bit.solutions.parking_system.dto.RateResponseDTO;
import com._bit.solutions.parking_system.dto.RateUpdateDTO;
import com._bit.solutions.parking_system.entity.Rate;

public final class RateMapper {
    private RateMapper() {}
    public static Rate toEntity(RateCreateDTO dto) {
        if (dto == null) return null;

        return Rate.builder()
                .type(dto.getType())
                .pricePerMinute(dto.getPricePerMinute())
                .build();
    }
    public static RateResponseDTO toDTO(Rate rate) {
        if (rate == null) return null;

        return RateResponseDTO.builder()
                .id(rate.getId())
                .type(rate.getType())
                .pricePerMinute(rate.getPricePerMinute())
                .build();
    }
    public static void updateEntity(Rate existing, RateUpdateDTO dto) {
        if (existing == null || dto == null) return;

        if (dto.getType() != null) {
            existing.setType(dto.getType());
        }

        if (dto.getPricePerMinute() != null) {
            existing.setPricePerMinute(dto.getPricePerMinute());
        }
    }
}
