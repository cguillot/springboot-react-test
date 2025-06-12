package com.cguillot.insuranceapp.spring;

import com.cguillot.insuranceapp.common.dto.ApiErrorResponseDTO;
import com.cguillot.insuranceapp.common.error.ApiFieldValidationErrorDTO;
import com.cguillot.insuranceapp.common.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {
        final var errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> {
                    System.out.println(error);
                    return ApiFieldValidationErrorDTO.of(error.getField(), error.getDefaultMessage());
                })
                .toList();

        final var payload = ApiErrorResponseDTO
                .builder()
                .error("Bad Request")
                .message("Validation error")
                .fieldErrors(errors)
                .build();

        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleNotFound(NotFoundException ex) {
        final var payload = ApiErrorResponseDTO
                .builder()
                .error("Not found")
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(payload);
    }
}