package com.cguillot.insuranceapp.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class ApiPaginatedResponseDTO<T> {
    private final List<T> data;

    // Pagination metadata
    private final long dataCount;
    private final long totalCount;

}
