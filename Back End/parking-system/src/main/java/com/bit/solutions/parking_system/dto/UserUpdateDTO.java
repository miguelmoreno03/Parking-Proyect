package com.bit.solutions.parking_system.dto;

import com.bit.solutions.parking_system.entity.enums.Role;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {

    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;

    private Role role;
    private Boolean active;
}