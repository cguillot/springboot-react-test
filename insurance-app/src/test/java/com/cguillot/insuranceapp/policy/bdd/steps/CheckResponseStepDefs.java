package com.cguillot.insuranceapp.policy.bdd.steps;

import com.cguillot.insuranceapp.common.dto.ApiPaginatedResponseDTO;
import com.cguillot.insuranceapp.common.dto.ApiResponseDTO;
import com.cguillot.insuranceapp.policy.PolicyRepository;
import com.cguillot.insuranceapp.policy.dto.PolicyDTO;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

public class CheckResponseStepDefs {
    @Autowired
    private TestContext testContext;

    @Autowired
    private PolicyRepository policyRepository;

    @Then("I should get {} paginated policies with total of {int}")
    public void iShouldGetPaginatedPolicies(int expectedPageCount, int expectedTotalCount) {
        final var lastResponse = testContext.getLatestResponse();

        assertEquals(HttpStatus.OK, lastResponse.getStatusCode());

        boolean samePageCount = false;
        boolean sameTotalCount = false;

        if (lastResponse.getBody() != null) {
            @SuppressWarnings("unchecked") final var responseBody = (ApiPaginatedResponseDTO<PolicyDTO>) lastResponse.getBody();
            assertNotNull(responseBody.getData(), "Paginated response payload should not be null");

            samePageCount = responseBody.getData().size() == responseBody.getDataCount()
                    && responseBody.getDataCount() == expectedPageCount;

            sameTotalCount = responseBody.getTotalCount() == expectedTotalCount;
        }

        assertTrue(samePageCount, "Expected page count: " + expectedPageCount + ", but got: " + lastResponse.getBody());
        assertTrue(sameTotalCount, "Expected total count: " + expectedTotalCount + ", but got: " + lastResponse.getBody());
    }

    @Then("the policy should be created successfully")
    public void thePolicyShouldBeCreatedSuccessfully() {
        final var lastResponse = testContext.getLatestResponse();

        assertEquals(HttpStatus.OK, lastResponse.getStatusCode());
        assertNotNull(lastResponse.getBody());

        @SuppressWarnings("unchecked") final var responseBody = (ApiResponseDTO<PolicyDTO>) lastResponse.getBody();

        assertNotNull(responseBody.getData(), "Response payload should not be null");
        assertNotNull(responseBody.getData().getId(), "Response payload should contain policy id");

        // Check into DB
        final var createdPolicy = policyRepository.findById(responseBody.getData().getId()).orElse(null);
        assertNotNull(createdPolicy, "Policy should be created in database");
    }

    @And("the policy should have the following details:")
    public void thePolicyShouldHaveTheFollowingDetails(DataTable dataTable) {
        final var expectedData = dataTable.asMap(String.class, String.class);

        // Retrieve policy from response
        final var lastResponse = testContext.getLatestResponse();
        @SuppressWarnings("unchecked") final var responseBody = (ApiResponseDTO<PolicyDTO>) lastResponse.getBody();
        assertNotNull(responseBody.getData(), "Response payload should not be null");

        final var createdPolicyDTO = responseBody.getData();
        assertNotNull(createdPolicyDTO);
        assertEquals(expectedData.get("name"), createdPolicyDTO.getName());
        assertEquals(expectedData.get("status"), createdPolicyDTO.getStatus().toString());
        assertEquals(LocalDate.parse(expectedData.get("startDate")), createdPolicyDTO.getStartDate());
        assertEquals(LocalDate.parse(expectedData.get("endDate")), createdPolicyDTO.getEndDate());

        // Verify the policy was created by checking the database
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        final var createdPolicy = policyRepository.findById(responseBody.getData().getId()).orElse(null);
        // Check into DB
        assertNotNull(createdPolicy, "Policy should be created in database");

        assertNotNull(createdPolicy);
        assertEquals(expectedData.get("name"), createdPolicy.getName());
        assertEquals(expectedData.get("status"), createdPolicy.getStatus().toString());
        assertEquals(LocalDate.parse(expectedData.get("startDate")), createdPolicy.getStartDate());
        assertEquals(LocalDate.parse(expectedData.get("endDate")), createdPolicy.getEndDate());
    }
}
