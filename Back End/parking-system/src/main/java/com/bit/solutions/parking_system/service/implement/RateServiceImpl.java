package com.bit.solutions.parking_system.service.implement;
import com.bit.solutions.parking_system.entity.enums.Type;
import com.bit.solutions.parking_system.entity.Rate;
import com.bit.solutions.parking_system.exceptions.BadRequestException;
import com.bit.solutions.parking_system.exceptions.ResourceNotFoundException;
import com.bit.solutions.parking_system.repository.RateRepository;
import com.bit.solutions.parking_system.service.interfaces.RateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RateServiceImpl implements RateService {

    private final RateRepository rateRepository;

    @Override
    public List<Rate> getAllRates() {
        log.debug("Fetching all rates");
        return rateRepository.findAll();
    }

    @Override
    public Rate getRateById(Long id) {
        log.debug("Fetching rate by id={}", id);
        return rateRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Rate not found with id={}", id);
                    return new ResourceNotFoundException("Rate not found with id " + id);
                });
    }

    @Override
    public Rate getRateByType(Type type) {
        log.debug("Fetching rate by type={}", type);
        return rateRepository.findByType(type)
                .orElseThrow(() -> {
                    log.warn("Rate not found with type={}", type);
                    return new ResourceNotFoundException("Rate not found with type " + type);
                });
    }

    @Override
    public Rate createRate(Rate rate) {
        log.debug("Creating rate: {}", rate);
        if (rate.getType() == null) {
            log.warn("Attempted to create rate without type");
            throw new BadRequestException("The type is required");
        }
       validatePrice(rate.getPricePerMinute());
        validateUniqueType(rate.getType());

        return rateRepository.save(rate);
    }


    @Override
    public Rate updateRate(Long id, Rate rate) {
        log.debug("Updating rate with id={}", id);
        Rate existing = rateRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Rate not found for update with id={}", id);
                    return new ResourceNotFoundException("Rate not found for update with id: " + id);
                });
       validatePrice(rate.getPricePerMinute());
        if (rate.getType() != null && !rate.getType().equals(existing.getType())) {
            validateUniqueType(rate.getType());
            existing.setType(rate.getType());
        }
        if (rate.getPricePerMinute() != null) {
            existing.setPricePerMinute(rate.getPricePerMinute());
        }
        return rateRepository.save(existing);
    }


    @Override
    public void deleteRate(Long id) {
        log.debug("Deleting rate with id={}", id);
        Rate existing = rateRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Rate not found for deletion with id={}", id);
                    return new ResourceNotFoundException("Rate not found with id: " + id);
                });
        rateRepository.delete(existing);
        log.info("Rate deleted successfully with id={}", id);
    }

    private void validatePrice(Double price) {
        if (price != null && price <= 0) {
            log.warn("Invalid price value {}", price);
            throw new BadRequestException("The price per minute must be greater than zero");
        }
    }

    private void validateUniqueType(Type type) {
        if (rateRepository.findByType(type).isPresent()) {
            log.warn("A rate with the specified type already exists={}", type);
            throw new BadRequestException("A rate for this type already exists");
        }
    }
}
