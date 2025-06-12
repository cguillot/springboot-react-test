package com.cguillot.insuranceapp.policy;

import com.cguillot.insuranceapp.policy.domain.InsurancePolicy;
import com.cguillot.insuranceapp.policy.dto.PolicyCreateDTO;
import com.cguillot.insuranceapp.policy.dto.PolicyStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PolicyServiceTest {

    @Mock
    private PolicyRepository policyRepository;

    @Spy
    private PolicyMapper policyMapper = new PolicyMapperImpl();

    @InjectMocks
    private PolicyService policyService;

    @Test
    void shouldCreatePolicy() {
        // Given
        final var inputPolicy = PolicyCreateDTO
                .builder()
                .name("Test Insurance")
                .status(PolicyStatus.ACTIVE)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusYears(1))
                .build();

        when(policyRepository.save(any(InsurancePolicy.class))).thenAnswer(invocation -> {
            final InsurancePolicy savedPolicy = invocation.getArgument(0);
            savedPolicy.setId(1L);

            assertNull(savedPolicy.getCreatedAt());
            assertNull(savedPolicy.getUpdatedAt());

            savedPolicy.setCreatedAt(Instant.now());
            savedPolicy.setUpdatedAt(Instant.now());
            return savedPolicy;
        });

        // When
        final var createdPolicy = policyService.createPolicy(inputPolicy);

        // Then
        assertNotNull(createdPolicy);
        assertNotNull(createdPolicy.getId());
        assertNotNull(createdPolicy.getCreatedAt());
        assertNotNull(createdPolicy.getUpdatedAt());
        assertEquals("Test Insurance", createdPolicy.getName());
        assertEquals(PolicyStatus.ACTIVE, createdPolicy.getStatus());
        assertEquals(inputPolicy.getStartDate(), createdPolicy.getStartDate());
        assertEquals(inputPolicy.getEndDate(), createdPolicy.getEndDate());
        verify(policyRepository).save(any(InsurancePolicy.class));
    }

}
