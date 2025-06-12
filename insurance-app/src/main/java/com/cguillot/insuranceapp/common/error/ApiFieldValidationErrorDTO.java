package com.cguillot.insuranceapp.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class ApiFieldValidationErrorDTO {
    private String field;
    private String message;
}
