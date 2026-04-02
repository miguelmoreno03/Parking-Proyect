package com._bit.solutions.parking_system.service.interfaces;

import com._bit.solutions.parking_system.entity.Enum.Type;
import com._bit.solutions.parking_system.entity.Rate;

import java.util.List;

public interface RateService {

    List<Rate> getAllRates();

    Rate getRateById (Long id);

    Rate getRateByType(Type type);

    Rate createRate(Rate rate);

    Rate updateRate(Long id, Rate rate);

    void deleteRate(Long id);

}
