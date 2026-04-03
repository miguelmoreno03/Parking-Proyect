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
        log.debug("Obteniendo todos los rates");
        return rateRepository.findAll();
    }

    @Override
    public Rate getRateById(Long id) {
        log.debug("Buscando rate por id={}", id);
        return rateRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Rate no encontrado con id={}", id);
                    return new ResourceNotFoundException("Rate no encontrado con id: " + id);
                });
    }

    @Override
    public Rate getRateByType(Type type) {
        log.debug("Buscando rate por type={}", type);
        return rateRepository.findByType(type)
                .orElseThrow(() -> {
                    log.warn("Rate no encontrado con type={}", type);
                    return new ResourceNotFoundException("Rate no encontrado con tipo: " + type);
                });
    }

    @Override
    public Rate createRate(Rate rate) {
        log.debug("Creando rate: {}", rate);
        if (rate.getType() == null) {
            log.warn("Intento de crear rate sin tipo");
            throw new BadRequestException("El tipo es obligatorio");
        }
       validatePrice(rate.getPricePerMinute());
        validateUniqueType(rate.getType());

        return rateRepository.save(rate);
    }


    @Override
    public Rate updateRate(Long id, Rate rate) {
        log.debug("Actualizando rate con id={}", id);
        Rate existing = rateRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Rate no encontrado para actualizar id={}", id);
                    return new ResourceNotFoundException("Rate no encontrado con id: " + id);
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
        log.debug("Eliminando rate con id={}", id);
        Rate existing = rateRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Rate no encontrado para eliminar id={}", id);
                    return new ResourceNotFoundException("Rate no encontrado con id: " + id);
                });
        rateRepository.delete(existing);
        log.info("Rate eliminado con id={}", id);
    }

    private void validatePrice(Double price) {
        if (price != null && price <= 0) {
            log.warn("Precio inválido: {}", price);
            throw new BadRequestException("El precio por minuto debe ser mayor a 0");
        }
    }

    private void validateUniqueType(Type type) {
        if (rateRepository.findByType(type).isPresent()) {
            log.warn("Ya existe un rate con type={}", type);
            throw new BadRequestException("Ya existe un rate con este tipo");
        }
    }
}
