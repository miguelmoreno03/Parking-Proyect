package com.bit.solutions.parking_system.controller;
import com.bit.solutions.parking_system.dto.ConfigurationCreateDTO;
import com.bit.solutions.parking_system.dto.ConfigurationResponseDTO;
import com.bit.solutions.parking_system.dto.ConfigurationUpdateDTO;
import com.bit.solutions.parking_system.dto.OccupancyResponseDTO;
import com.bit.solutions.parking_system.entity.Configuration;
import com.bit.solutions.parking_system.mappers.ConfigurationMapper;
import com.bit.solutions.parking_system.service.interfaces.ConfigurationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/config")
@RequiredArgsConstructor
public class ConfigurationController {
    private final ConfigurationService configurationService;
    @GetMapping("/occupancy")
    public ResponseEntity<OccupancyResponseDTO> getCurrentOccupancy() {
        return ResponseEntity.ok(configurationService.getCurrentOccupancy());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ConfigurationResponseDTO> getById(@PathVariable Long id) {
        Configuration config = configurationService.getConfigurationById(id);
        if (config == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ConfigurationMapper.toDTO(config));
    }

    @PostMapping
    public ResponseEntity<ConfigurationResponseDTO> create(
            @Valid @RequestBody ConfigurationCreateDTO dto) {
        Configuration config = ConfigurationMapper.toEntity(dto);
        Configuration saved = configurationService.createConfiguration(config);

        return ResponseEntity
                .created(URI.create("/config/" + saved.getId()))
                .body(ConfigurationMapper.toDTO(saved));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ConfigurationResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ConfigurationUpdateDTO dto) {

        Configuration config = ConfigurationMapper.toEntity(dto);
        Configuration updated = configurationService.updateConfiguration(id, config);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ConfigurationMapper.toDTO(updated));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        configurationService.deleteConfiguration(id);
        return ResponseEntity.ok("Configuration with id " + id + " was successfully deleted");
    }

}
