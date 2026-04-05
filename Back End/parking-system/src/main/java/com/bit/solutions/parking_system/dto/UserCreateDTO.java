package com.bit.solutions.parking_system.dto;

import com.bit.solutions.parking_system.entity.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDTO {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank (message = "Username is required")
    private String userName;
    @NotBlank(message = "Password is required")
    private String password;
    @NotNull(message = "Role is required")
    private Role role;
}
