package com.bit.solutions.parking_system.service.interfaces;

import com.bit.solutions.parking_system.entity.enums.Type;
import com.bit.solutions.parking_system.entity.Rate;

import java.util.List;

public interface RateService {

    List<Rate> getAllRates();

    Rate getRateById (Long id);

    Rate getRateByType(Type type);

    Rate createRate(Rate rate);

    Rate updateRate(Long id, Rate rate);

    void deleteRate(Long id);

}
