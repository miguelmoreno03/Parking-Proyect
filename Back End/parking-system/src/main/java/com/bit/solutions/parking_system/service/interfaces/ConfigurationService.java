package com.bit.solutions.parking_system.service.interfaces;

import com.bit.solutions.parking_system.dto.OccupancyResponseDTO;
import com.bit.solutions.parking_system.entity.Configuration;

public interface ConfigurationService {
    Configuration createConfiguration(Configuration config);
    Configuration getConfigurationById (Long id);
    Configuration updateConfiguration (Long ig,Configuration config);
    void deleteConfiguration (Long id);
    void decreaseAvailableSpaces();
    void increaseAvailableSpaces();
    OccupancyResponseDTO getCurrentOccupancy();
}
