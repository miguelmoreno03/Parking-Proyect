package com.bit.solutions.parking_system.service.implement;

import com.bit.solutions.parking_system.dto.OccupancyResponseDTO;
import com.bit.solutions.parking_system.entity.Configuration;
import com.bit.solutions.parking_system.exceptions.BadRequestException;
import com.bit.solutions.parking_system.exceptions.ResourceNotFoundException;
import com.bit.solutions.parking_system.repository.ConfigurationRepository;
import com.bit.solutions.parking_system.service.interfaces.ConfigurationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConfigurationServiceImpl implements ConfigurationService {
    private final ConfigurationRepository configurationRepository;

    @Override
    public Configuration createConfiguration(Configuration config) {
        log.debug("Creating Configuration: {}", config);
        if (configurationRepository.count() > 0) {
            log.warn("Attempt to create more than one configuration");
            throw new BadRequestException("A configuration already exists");
        }
        validateCapacity(config.getTotalCapacity());

        config.setAvailableSpaces(config.getTotalCapacity());
        return configurationRepository.save(config);
    }

    @Override
    public Configuration getConfigurationById(Long id) {
        log.debug("Fetching configuration by id={}", id);
        return configurationRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Configuration not found with id={}", id);
                    return new ResourceNotFoundException("Configuration not found with id " + id);
                });
    }

    @Override
    public Configuration updateConfiguration(Long id, Configuration config) {
        log.debug("Updating configuration with id={}", id);

        Configuration existing = configurationRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Configuration not found for update with id={}", id);
                    return new ResourceNotFoundException("Configuration not found with id: " + id);
                });

        if (config.getTotalCapacity() != null) {

            validateCapacity(config.getTotalCapacity());

            int difference = config.getTotalCapacity() - existing.getTotalCapacity();

            existing.setTotalCapacity(config.getTotalCapacity());
            existing.setAvailableSpaces(existing.getAvailableSpaces() + difference);
        }

        return configurationRepository.save(existing);
    }

    @Override
    public void deleteConfiguration(Long id) {
        log.debug("Deleting configuration with id={}", id);

        Configuration existing = configurationRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Configuration not found for deletion with id={}", id);
                    return new ResourceNotFoundException("Configuration not found with id: " + id);
                });

        configurationRepository.delete(existing);

        log.info("Configuration deleted successfully with id={}", id);
    }

    @Override
    public void decreaseAvailableSpaces() {
        log.debug("Decreasing available spaces");
        Configuration config = getSingleConfiguration();
        if (config.getAvailableSpaces() <= 0) {
            log.warn("No available spaces left");
            throw new BadRequestException("No available spaces");
        }
        config.setAvailableSpaces(config.getAvailableSpaces() - 1);
        configurationRepository.save(config);
        log.info("Space occupied. Available spaces={}", config.getAvailableSpaces());
    }

    @Override
    public void increaseAvailableSpaces() {
        log.debug("Increasing available spaces");
        Configuration config = getSingleConfiguration();
        if (config.getAvailableSpaces() >= config.getTotalCapacity()) {
            log.warn("Parking is already empty");
            throw new BadRequestException("Parking is already empty");
        }
        config.setAvailableSpaces(config.getAvailableSpaces() + 1);
        configurationRepository.save(config);
        log.info("Space released. Available spaces={}", config.getAvailableSpaces());
    }

    @Override
    public OccupancyResponseDTO getCurrentOccupancy() {
        log.debug("Fetching current occupancy");
        Configuration config = getSingleConfiguration();
        int totalCapacity = config.getTotalCapacity();
        int availableSpaces = config.getAvailableSpaces();
        int occupiedSpaces = totalCapacity - availableSpaces;
        double occupancyPercentage = totalCapacity > 0
                ? (occupiedSpaces * 100.0) / totalCapacity
                : 0.0;
        return OccupancyResponseDTO.builder()
                .totalCapacity(totalCapacity)
                .availableSpaces(availableSpaces)
                .occupiedSpaces(occupiedSpaces)
                .occupancyPercentage(occupancyPercentage)
                .build();
    }

    private Configuration getSingleConfiguration() {
        return configurationRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Configuration not found"));
    }

    private void validateCapacity(Integer capacity) {
        if (capacity == null || capacity <= 0) {
            log.warn("Invalid capacity value {}", capacity);
            throw new BadRequestException("Total capacity must be greater than zero");
        }
    }
}
