package com.cguillot.insuranceapp.policy;


import com.cguillot.insuranceapp.policy.domain.InsurancePolicy;
import com.cguillot.insuranceapp.policy.dto.PaginatedSearchResultDTO;
import com.cguillot.insuranceapp.policy.dto.PolicyCreateDTO;
import com.cguillot.insuranceapp.policy.dto.PolicyDTO;
import com.cguillot.insuranceapp.policy.dto.PolicyUpdateDTO;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PolicyMapper {

    public LocalDateTime localDateTimeFromInstant(Instant instant) {
        return instant == null ? null : LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }

    public OffsetDateTime offsetDateTimeFromInstant(Instant instant) {
        return instant == null ? null : OffsetDateTime.ofInstant(instant, ZoneOffset.UTC);
    }

    public abstract PolicyDTO policyDomainToPolicyDTO(final InsurancePolicy policy);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    public abstract List<PolicyDTO> listPolicyEntityToListPolicyDTO(final List<InsurancePolicy> policies);

    @Mapping(target = "data", expression = "java(listPolicyEntityToListPolicyDTO(findResult.getContent()))")
    @Mapping(target = "dataCount", source = "numberOfElements")
    @Mapping(target = "totalCount", source = "totalElements")
    public abstract PaginatedSearchResultDTO<PolicyDTO> pageToPaginatedSearchResultDTO(final Page<InsurancePolicy> findResult);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract InsurancePolicy policyCreateDTOtoPolicyDomain(final PolicyCreateDTO policy);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract InsurancePolicy updatePolicyDomainWithPolicyUpdateDTO(final PolicyUpdateDTO policyUpdateDTO, @MappingTarget InsurancePolicy policy);
}
