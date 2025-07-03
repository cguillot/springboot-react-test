package com.cguillot.insuranceapp.policy.bdd.steps;

import com.cguillot.insuranceapp.common.dto.ApiRequestDTO;
import com.cguillot.insuranceapp.common.dto.ApiResponseDTO;
import com.cguillot.insuranceapp.policy.dto.PolicyCreateDTO;
import com.cguillot.insuranceapp.policy.dto.PolicyDTO;
import com.cguillot.insuranceapp.policy.dto.PolicyStatus;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.time.LocalDate;

public class CreatePolicyStepDefs {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TestContext testContext;

    @When("I create a new insurance policy with the following details:")
    public void iCreateANewInsurancePolicyWithTheFollowingDetails(DataTable dataTable) {
        final var policyData = dataTable.asMap(String.class, String.class);

        final var createDTO = PolicyCreateDTO.builder()
                .name(policyData.get("name"))
                .status(PolicyStatus.valueOf(policyData.get("status")))
                .startDate(LocalDate.parse(policyData.get("startDate")))
                .endDate(LocalDate.parse(policyData.get("endDate")))
                .build();

        // Create request
        final var createRequestDTO = new ApiRequestDTO<>(createDTO);

        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final var request = new HttpEntity<>(createRequestDTO, headers);

        // Expected response type
        final var responseType = new ParameterizedTypeReference<ApiResponseDTO<PolicyDTO>>() {
        };

        final var lastResponse = restTemplate.exchange(
                "/api/v1/policies",
                HttpMethod.POST,
                request,
                responseType
        );

        testContext.setLatestResponse(lastResponse);
    }

}
