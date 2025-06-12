package com.cguillot.insuranceapp.policy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Builder
@Jacksonized
public class PolicyDTO {

    /*
        Identifiant de la police
    */
    private Long id;

    /*
        Nom de la police
     */
    private String name;

    /*
        Status de la police
     */
    private PolicyStatus status;

    /*
        Date de début de couverture
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    /*
        Date de fin de couverture
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    /*
        Date de creation
     */
    private OffsetDateTime createdAt;

    /*
        Date de mise a jour
     */
    private OffsetDateTime updatedAt;
}
