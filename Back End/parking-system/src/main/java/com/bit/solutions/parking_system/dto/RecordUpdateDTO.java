package com.bit.solutions.parking_system.dto;

import com.bit.solutions.parking_system.entity.enums.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordUpdateDTO {
    private String plate;
    private Type type;
}
