package com.bit.solutions.parking_system.controller;
import com.bit.solutions.parking_system.dto.ConfigurationCreateDTO;
import com.bit.solutions.parking_system.dto.ConfigurationResponseDTO;
import com.bit.solutions.parking_system.dto.ConfigurationUpdateDTO;
import com.bit.solutions.parking_system.entity.Configuration;
import com.bit.solutions.parking_system.mappers.ConfigurationMapper;
import com.bit.solutions.parking_system.service.interfaces.ConfigurationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config")
@RequiredArgsConstructor
public class ConfigurationController {
    private final ConfigurationService configurationService;

    @GetMapping("/{id}")
    public ConfigurationResponseDTO getById(@PathVariable Long id) {
        Configuration config = configurationService.getConfigurationById(id);
        return ConfigurationMapper.toDTO(config);
    }

    @PostMapping
    public ConfigurationResponseDTO create(
            @Valid @RequestBody ConfigurationCreateDTO dto) {

        Configuration config = ConfigurationMapper.toEntity(dto);
        Configuration saved = configurationService.createConfiguration(config);

        return ConfigurationMapper.toDTO(saved);
    }

    @PatchMapping("/{id}")
    public ConfigurationResponseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody ConfigurationUpdateDTO dto) {

        Configuration config = ConfigurationMapper.toEntity(dto);
        Configuration updated = configurationService.updateConfiguration(id, config);

        return ConfigurationMapper.toDTO(updated);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        configurationService.deleteConfiguration(id);
        return ResponseEntity.ok("Configuration with id " + id + " was successfully deleted");
    }

}
