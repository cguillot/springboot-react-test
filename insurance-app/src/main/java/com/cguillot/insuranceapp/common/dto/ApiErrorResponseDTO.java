package com.cguillot.insuranceapp.common.dto;

import com.cguillot.insuranceapp.common.error.ApiFieldValidationErrorDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ApiErrorResponseDTO {
    private String error;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ApiFieldValidationErrorDTO> fieldErrors;

}
