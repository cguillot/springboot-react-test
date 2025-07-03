package com.cguillot.insuranceapp.policy.bdd.steps;

import com.cguillot.insuranceapp.common.dto.ApiPaginatedResponseDTO;
import com.cguillot.insuranceapp.policy.PolicyRepository;
import com.cguillot.insuranceapp.policy.dto.PolicyDTO;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

public class SearchPolicyStepDefs {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private TestContext testContext;

    @When("I request all policies")
    public void iRequestAllPolicies() {
        final var responseType = new ParameterizedTypeReference<ApiPaginatedResponseDTO<PolicyDTO>>() {
        };

        final var lastResponse = restTemplate.exchange(
                "/api/v1/policies",
                HttpMethod.GET,
                null,
                responseType
        );

        testContext.setLatestResponse(lastResponse);
    }


}
