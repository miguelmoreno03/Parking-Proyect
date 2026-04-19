package com.bit.solutions.parking_system.security.auth.controller;

import com.bit.solutions.parking_system.security.auth.DTO.AuthenticationResponseDTO;
import com.bit.solutions.parking_system.security.auth.DTO.LoginRequestDTO;
import com.bit.solutions.parking_system.security.auth.DTO.LoginResponseDTO;
import com.bit.solutions.parking_system.security.auth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request,
            HttpServletResponse response
    ) {
        AuthenticationResponseDTO authResponse = authService.login(request);

        addRefreshTokenCookie(response, authResponse.getRefreshToken());

        LoginResponseDTO body = LoginResponseDTO.builder()
                .accessToken(authResponse.getAccessToken())
                .type("Bearer")
                .role(authResponse.getRole())
                .build();

        return ResponseEntity.ok(body);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(
            @CookieValue(value = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response
    ) {
        AuthenticationResponseDTO authResponse = authService.refreshToken(refreshToken);

        addRefreshTokenCookie(response, authResponse.getRefreshToken());

        LoginResponseDTO body = LoginResponseDTO.builder()
                .accessToken(authResponse.getAccessToken())
                .type("Bearer")
                .role(authResponse.getRole())
                .build();

        return ResponseEntity.ok(body);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        clearRefreshTokenCookie(response);
        return ResponseEntity.ok("Logout successful");
    }

    private void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/auth")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Lax")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
    }

    private void clearRefreshTokenCookie(HttpServletResponse response) {
        ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/auth")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());
    }
}