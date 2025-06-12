package com.cguillot.insuranceapp.policy;

import com.cguillot.insuranceapp.common.dto.ApiPaginatedResponseDTO;
import com.cguillot.insuranceapp.common.dto.ApiRequestDTO;
import com.cguillot.insuranceapp.common.dto.ApiResponseDTO;
import com.cguillot.insuranceapp.common.error.NotFoundException;
import com.cguillot.insuranceapp.policy.dto.PolicyCreateDTO;
import com.cguillot.insuranceapp.policy.dto.PolicyDTO;
import com.cguillot.insuranceapp.policy.dto.PolicyUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Endpoint fournis:
 * - lister les polices d’assurance
 * - Créer une police d’assurance
 * - Lire une police d’assurance
 * - Editer une police d’assurance
 */
@RestController
@RequestMapping("/api/v1")
class PolicyEndpoint {

    final PolicyService policyService;

    PolicyEndpoint(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping("/policies")
    ApiPaginatedResponseDTO<PolicyDTO> searchPolicies(final Pageable pageable) {
        final var pagedResponse = policyService.searchPolicies(pageable);

        return ApiPaginatedResponseDTO.of(
                pagedResponse.getData(),
                pagedResponse.getDataCount(),
                pagedResponse.getTotalCount());
    }

    @PostMapping("/policies")
    ApiResponseDTO<PolicyDTO> createPolicy(final @RequestBody @Valid ApiRequestDTO<PolicyCreateDTO> policyCreateRequestDTO) {
        final var newPolicy = policyService.createPolicy(policyCreateRequestDTO.getData());

        return ApiResponseDTO.of(newPolicy);
    }

    @GetMapping("/policies/{policyId}")
    ApiResponseDTO<PolicyDTO> getPolicy(final @PathVariable Long policyId) {
        return policyService.getPolicy(policyId)
                .map(ApiResponseDTO::of)
                .orElseThrow(() -> new NotFoundException("Policy with ID " + policyId + " does not exist"));
    }

    @PutMapping("/policies/{policyId}")
    ApiResponseDTO<PolicyDTO> updatePolicy(final @PathVariable Long policyId, final @Valid @RequestBody ApiRequestDTO<PolicyUpdateDTO> policyUpdateRequestDTO) {
        final var updatedPolicy = policyService.updatePolicy(policyId, policyUpdateRequestDTO.getData());

        return ApiResponseDTO.of(updatedPolicy);
    }

    @DeleteMapping("/policies/{policyId}")
    ResponseEntity<Void> deletePolicy(final @PathVariable Long policyId) {
        policyService.deletePolicy(policyId);

        return ResponseEntity.noContent().build();
    }
}
