package com.bit.solutions.parking_system.mappers;
import com.bit.solutions.parking_system.dto.ConfigurationCreateDTO;
import com.bit.solutions.parking_system.dto.ConfigurationResponseDTO;
import com.bit.solutions.parking_system.dto.ConfigurationUpdateDTO;
import com.bit.solutions.parking_system.entity.Configuration;

public final class ConfigurationMapper {
    private ConfigurationMapper(){}
        // CREATE
        public static Configuration toEntity(ConfigurationCreateDTO dto) {
            if (dto == null) return null;

            return Configuration.builder()
                    .totalCapacity(dto.getTotalCapacity())
                    .build();
        }

        // UPDATE
        public static Configuration toEntity(ConfigurationUpdateDTO dto) {
            if (dto == null) return null;

            return Configuration.builder()
                    .totalCapacity(dto.getTotalCapacity())
                    .build();
        }

        // RESPONSE
        public static ConfigurationResponseDTO toDTO(Configuration entity) {
            if (entity == null) return null;

            return ConfigurationResponseDTO.builder()
                    .id(entity.getId())
                    .totalCapacity(entity.getTotalCapacity())
                    .availableSpaces(entity.getAvailableSpaces())
                    .build();
        }
}

