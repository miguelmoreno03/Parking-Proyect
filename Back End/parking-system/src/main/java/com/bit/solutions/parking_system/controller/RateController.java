package com.bit.solutions.parking_system.controller;

import com.bit.solutions.parking_system.dto.RateCreateDTO;
import com.bit.solutions.parking_system.dto.RateResponseDTO;
import com.bit.solutions.parking_system.dto.RateUpdateDTO;
import com.bit.solutions.parking_system.entity.enums.Type;
import com.bit.solutions.parking_system.entity.Rate;
import com.bit.solutions.parking_system.mappers.RateMapper;
import com.bit.solutions.parking_system.service.interfaces.RateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/rates")
@RequiredArgsConstructor
public class RateController {
    private final RateService rateService;

    @GetMapping
    public ResponseEntity<List<RateResponseDTO>> getAll() {
        List<Rate> rates = rateService.getAllRates();
        List<RateResponseDTO> response = new ArrayList<>();
        for (Rate rate : rates) {
            response.add(RateMapper.toDTO(rate));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RateResponseDTO> getById(@PathVariable Long id) {
        Rate rate = rateService.getRateById(id);
        if (rate == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(RateMapper.toDTO(rate));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity <RateResponseDTO> getByType(@PathVariable Type type) {
        Rate rate = rateService.getRateByType(type);
        if (rate == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(RateMapper.toDTO(rate));
    }

    @PostMapping
    public ResponseEntity< RateResponseDTO> create(@Valid @RequestBody RateCreateDTO dto) {
        Rate rate = RateMapper.toEntity(dto);
        Rate saved = rateService.createRate(rate);
        return ResponseEntity
                .created(URI.create("/rates/"+saved.getId()))
                .body(RateMapper.toDTO(saved));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RateResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody RateUpdateDTO dto) {

        Rate rate = new Rate();
        rate.setType(dto.getType());
        rate.setPricePerMinute(dto.getPricePerMinute());

        Rate updated = rateService.updateRate(id, rate);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(RateMapper.toDTO(updated));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        rateService.deleteRate(id);
        return ResponseEntity.ok("Rate with id "+id+" was successfully deleted");
    }

}
