package com.cguillot.insuranceapp.policy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Getter
@Builder
@Jacksonized
public class PolicyCreateDTO {

    /*
        Nom de la police
        - Non null
        - Non vide
     */
    @NotBlank(message = "{insurance.policy.name.not_blank}")
    @Size(min = 2, max = 100, message = "{insurance.policy.name.bad_length}")
    private String name;

    /*
        Status de la police
        - Non null
     */
    @NotNull(message = "{insurance.policy.status.not_blank}")
    private PolicyStatus status;

    /*
        Date de début de couverture
        - Non null
        - Obligatoire
     */
    @NotNull(message = "{insurance.policy.start_date.not_blank}")
    private LocalDate startDate;

    /*
        Date de fin de couverture
        - Non null
        - Obligatoire
     */
    @NotNull(message = "{insurance.policy.end_date.not_blank}")
    private LocalDate endDate;
}
