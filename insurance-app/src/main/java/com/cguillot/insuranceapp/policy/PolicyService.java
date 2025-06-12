package com.cguillot.insuranceapp.policy;

import com.cguillot.insuranceapp.common.error.NotFoundException;
import com.cguillot.insuranceapp.policy.dto.PaginatedSearchResultDTO;
import com.cguillot.insuranceapp.policy.dto.PolicyCreateDTO;
import com.cguillot.insuranceapp.policy.dto.PolicyDTO;
import com.cguillot.insuranceapp.policy.dto.PolicyUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final PolicyMapper policyMapper;

    @Transactional(readOnly = true)
    public PaginatedSearchResultDTO<PolicyDTO> searchPolicies(final Pageable pageable) {
        final var findResult = policyRepository.findAll(pageable);

        return policyMapper.pageToPaginatedSearchResultDTO(findResult);
    }

    public PolicyDTO createPolicy(final PolicyCreateDTO policyCreateDTO) {
        final var entity = policyMapper.policyCreateDTOtoPolicyDomain(policyCreateDTO);
        final var created = policyRepository.save(entity);

        return policyMapper.policyDomainToPolicyDTO(created);
    }

    public PolicyDTO updatePolicy(Long policyId, final PolicyUpdateDTO policyUpdateDTO) {
        final var entity = policyRepository
                .findById(policyId)
                .orElseThrow(() -> new NotFoundException("Policy with ID " + policyId + " does not exist"));

        final var forUpdate = policyMapper.updatePolicyDomainWithPolicyUpdateDTO(policyUpdateDTO, entity);
        final var updated = policyRepository.save(forUpdate);

        return policyMapper.policyDomainToPolicyDTO(updated);
    }

    @Transactional(readOnly = true)
    public Optional<PolicyDTO> getPolicy(final Long policyId) {
        return policyRepository
                .findById(policyId)
                .map(policyMapper::policyDomainToPolicyDTO);
    }

    public void deletePolicy(final Long policyId) {
        if (!policyRepository.existsById(policyId)) {
            throw new NotFoundException("Policy with ID " + policyId + " does not exist");
        }

        policyRepository.deleteById(policyId);
    }
}
