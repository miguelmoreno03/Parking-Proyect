package com._bit.solutions.parking_system.exceptions;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private String message;
    private int status;
    private LocalDateTime timestamp;
    private String path;
    private String traceId;
}
