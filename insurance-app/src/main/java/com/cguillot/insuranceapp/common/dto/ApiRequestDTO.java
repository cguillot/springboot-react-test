package com.cguillot.insuranceapp.common.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiRequestDTO<T> {
    @Valid
    @NotNull(message = "You must provide a data payload")
    private T data;
}
