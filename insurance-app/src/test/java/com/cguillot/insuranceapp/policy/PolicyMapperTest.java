package com.cguillot.insuranceapp.policy;

import com.cguillot.insuranceapp.policy.domain.InsurancePolicy;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.*;

public class PolicyMapperTest {

    private final PolicyMapper policyMapper = new PolicyMapperImpl();

    @Test
    void shouldReturnEmptyListWhenNoItemOrNull() {
        final Page<InsurancePolicy> emptyPage = Page.empty();

        final var result = policyMapper.pageToPaginatedSearchResultDTO(emptyPage);

        assertNotNull(result);
        assertNotNull(result.getData());
        assertTrue(result.getData().isEmpty());
        assertEquals(0, result.getDataCount());
        assertEquals(0, result.getTotalCount());
    }

}
