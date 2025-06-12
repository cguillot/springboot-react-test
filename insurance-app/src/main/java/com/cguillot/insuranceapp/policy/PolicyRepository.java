package com.cguillot.insuranceapp.policy;

import com.cguillot.insuranceapp.policy.domain.InsurancePolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<InsurancePolicy, Long> {
}
