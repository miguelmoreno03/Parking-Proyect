package com._bit.solutions.parking_system.exceptions;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RestControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> processErrorNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        String traceId = UUID.randomUUID().toString();

        log.warn("HTTP 404 NOT_FOUND | traceId={} | path={} | method={} | exception={} | message={}",
                traceId,
                request.getRequestURI(),
                request.getMethod(),
                ex.getClass().getSimpleName(),
                ex.getMessage()
        );

        ErrorResponse error = ErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .traceId(traceId)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> processErrorBadRequest(
            BadRequestException ex,
            HttpServletRequest request) {

        String traceId = UUID.randomUUID().toString();

        log.warn("HTTP 400 BAD_REQUEST | traceId={} | path={} | method={} | exception={} | message={}",
                traceId,
                request.getRequestURI(),
                request.getMethod(),
                ex.getClass().getSimpleName(),
                ex.getMessage()
        );

        ErrorResponse error = ErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .traceId(traceId)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(
            Exception ex,
            HttpServletRequest request){

        String traceId = UUID.randomUUID().toString();

        log.error("HTTP 500 INTERNAL_SERVER_ERROR | traceId={} | path={} | method={} | exception={} | message={}",
                traceId,
                request.getRequestURI(),
                request.getMethod(),
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                ex
        );

        ErrorResponse error = ErrorResponse.builder()
                .message("Internal server error")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .traceId(traceId)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
