package com.bit.solutions.parking_system.dto;

import com.bit.solutions.parking_system.entity.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {
    private String name;
    private String username;
    private String password;
    private Role role;
    private Boolean active;
}
