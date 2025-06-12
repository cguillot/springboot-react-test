package com.cguillot.insuranceapp.policy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PaginatedSearchResultDTO<T> {

    private List<T> data;

    // Pagination metadata
    private final long dataCount;
    private final long totalCount;
}
