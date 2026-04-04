package com.bit.solutions.parking_system.mappers;
import com.bit.solutions.parking_system.dto.UserCreateDTO;
import com.bit.solutions.parking_system.dto.UserResponseDTO;
import com.bit.solutions.parking_system.dto.UserUpdateDTO;
import com.bit.solutions.parking_system.entity.User;

public final class UserMapper {
    private UserMapper() {}
    // CREATE
    public static User toEntity(UserCreateDTO dto) {
        if (dto == null) return null;
        return User.builder()
                .name(dto.getName())
                .username(dto.getUserName())
                .password(dto.getPassword())
                .role(dto.getRole())
                .active(true)
                .build();
    }
    // UPDATE
    public static void updateEntity(User existing, UserUpdateDTO dto) {
        if (existing == null || dto == null) return;

        if (dto.getName() != null) {
            existing.setName(dto.getName());
        }

        if (dto.getUsername() != null) {
            existing.setUsername(dto.getUsername());
        }

        if (dto.getPassword() != null) {
            existing.setPassword(dto.getPassword());
        }

        if (dto.getRole() != null) {
            existing.setRole(dto.getRole());
        }

        if (dto.getActive() != null) {
            existing.setActive(dto.getActive());
        }
    }

    // RESPONSE
    public static UserResponseDTO toDTO(User user) {
        if (user == null) return null;
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .role(user.getRole())
                .active(user.getActive())
                .createdAt(user.getCreatedAt())
                .build();
    }


}
