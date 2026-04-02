package com._bit.solutions.parking_system.controller;

import com._bit.solutions.parking_system.dto.RateCreateDTO;
import com._bit.solutions.parking_system.dto.RateResponseDTO;
import com._bit.solutions.parking_system.dto.RateUpdateDTO;
import com._bit.solutions.parking_system.entity.Enum.Type;
import com._bit.solutions.parking_system.entity.Rate;
import com._bit.solutions.parking_system.mappers.RateMapper;
import com._bit.solutions.parking_system.service.interfaces.RateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/rates")
@RequiredArgsConstructor
public class RateController {
    private final RateService rateService;

    @GetMapping
    public List<RateResponseDTO> getAll() {
        List<Rate> rates = rateService.getAllRates();
        List<RateResponseDTO> response = new java.util.ArrayList<>();
        for (Rate rate : rates) {
            response.add(RateMapper.toDTO(rate));
        }
        return response;
    }

    @GetMapping("/{id}")
    public RateResponseDTO getById(@PathVariable Long id) {
        Rate rate = rateService.getRateById(id);
        return RateMapper.toDTO(rate);
    }

    @GetMapping("/type/{type}")
    public RateResponseDTO getByType(@PathVariable Type type) {
        Rate rate = rateService.getRateByType(type);
        return RateMapper.toDTO(rate);
    }

    @PostMapping
    public RateResponseDTO create(@Valid @RequestBody RateCreateDTO dto) {
        Rate rate = RateMapper.toEntity(dto);
        Rate saved = rateService.createRate(rate);
        return RateMapper.toDTO(saved);
    }

    @PatchMapping("/{id}")
    public RateResponseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody RateUpdateDTO dto) {

        Rate rate = new Rate();
        rate.setType(dto.getType());
        rate.setPricePerMinute(dto.getPricePerMinute());

        Rate updated = rateService.updateRate(id, rate);
        return RateMapper.toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        rateService.deleteRate(id);
    }

}
