package com.bit.solutions.parking_system.security.auth.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AuthenticationResponseDTO {
    private String accessToken;
    private String refreshToken;
    private String role;
}