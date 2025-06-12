package com.cguillot.insuranceapp.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class ApiResponseDTO<T> {
    private T data;
}
