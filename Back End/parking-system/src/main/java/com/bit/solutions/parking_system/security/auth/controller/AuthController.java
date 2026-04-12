package com.bit.solutions.parking_system.security.auth.controller;
import com.bit.solutions.parking_system.security.auth.DTO.LoginRequestDTO;
import com.bit.solutions.parking_system.security.auth.DTO.LoginResponseDTO;
import com.bit.solutions.parking_system.security.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
