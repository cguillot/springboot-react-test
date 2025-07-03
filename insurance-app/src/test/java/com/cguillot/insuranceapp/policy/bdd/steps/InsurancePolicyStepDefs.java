package com.cguillot.insuranceapp.policy.bdd.steps;

/**
 * Step definitions for Insurance Policy BDD tests
 */
public class InsurancePolicyStepDefs {

    /*
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private ResponseEntity<?> lastResponse;
    private Long lastCreatedPolicyId;
    private ApiResponseDTO<PolicyDTO> lastPolicyResponse;
    private String lastErrorMessage;

    @Given("the insurance application is running")
    public void theInsuranceApplicationIsRunning() {
        // Application context is already loaded by Spring Boot test
        assertNotNull(restTemplate);
    }

    @Given("the database is clean")
    public void theDatabaseIsClean() {        policyRepository.deleteAll();
    }

    // Basic scenario step definitions for simple_policy.feature
    @When("I create a policy with name {string} and status {string}")
    public void iCreateAPolicyWithNameAndStatus(String policyName, String policyStatus) {
        PolicyCreateDTO createDTO = PolicyCreateDTO.builder()
            .name(policyName)
            .status(PolicyStatus.valueOf(policyStatus))
            .startDate(LocalDate.now())
            .endDate(LocalDate.now().plusYears(1))
            .build();
        
        ApiRequestDTO<PolicyCreateDTO> requestDTO = new ApiRequestDTO<>(createDTO);
        
        lastResponse = restTemplate.postForEntity("/api/v1/policies", requestDTO, ApiResponseDTO.class);
        
        if (lastResponse.getStatusCode() == HttpStatus.OK) {
            @SuppressWarnings("unchecked")
            ApiResponseDTO<PolicyDTO> responseBody = (ApiResponseDTO<PolicyDTO>) lastResponse.getBody();
            if (responseBody != null && responseBody.getPayload() != null) {
                lastCreatedPolicyId = responseBody.getPayload().getId();
            }
        }
    }

    @Given("I have a policy with name {string}")
    public void iHaveAPolicyWithName(String policyName) {
        InsurancePolicy policy = new InsurancePolicy();
        policy.setName(policyName);
        policy.setStatus(InsurancePolicy.Status.ACTIVE);
        policy.setStartDate(LocalDate.now());
        policy.setEndDate(LocalDate.now().plusYears(1));
        
        InsurancePolicy savedPolicy = policyRepository.save(policy);
        lastCreatedPolicyId = savedPolicy.getId();
    }

    @And("the policy should have name {string}")
    public void thePolicyShouldHaveName(String expectedName) {
        // Verify the policy name in the database
        InsurancePolicy policy = policyRepository.findById(lastCreatedPolicyId).orElse(null);
        assertNotNull(policy);
        assertEquals(expectedName, policy.getName());
    }

    @And("the policy should have status {string}")
    public void thePolicyShouldHaveStatus(String expectedStatus) {
        // Verify the policy status in the database
        InsurancePolicy policy = policyRepository.findById(lastCreatedPolicyId).orElse(null);
        assertNotNull(policy);
        assertEquals(expectedStatus, policy.getStatus().toString());
    }

    // Detailed scenario step definitions for insurance_policy.feature
    @When("I create a new insurance policy with the following details:")
    public void iCreateANewInsurancePolicyWithTheFollowingDetails(DataTable dataTable) {
        Map<String, String> policyData = dataTable.asMap(String.class, String.class);
        
        PolicyCreateDTO createDTO = PolicyCreateDTO.builder()
            .name(policyData.get("name"))
            .status(PolicyStatus.valueOf(policyData.get("status")))
            .startDate(LocalDate.parse(policyData.get("startDate")))
            .endDate(LocalDate.parse(policyData.get("endDate")))
            .build();
        
        ApiRequestDTO<PolicyCreateDTO> requestDTO = new ApiRequestDTO<>(createDTO);
        
        lastResponse = restTemplate.postForEntity("/api/v1/policies", requestDTO, ApiResponseDTO.class);
        
        if (lastResponse.getStatusCode() == HttpStatus.OK) {
            @SuppressWarnings("unchecked")
            ApiResponseDTO<PolicyDTO> responseBody = (ApiResponseDTO<PolicyDTO>) lastResponse.getBody();
            if (responseBody != null && responseBody.getPayload() != null) {
                lastCreatedPolicyId = responseBody.getPayload().getId();
            }
        }
    }

    @Then("the policy should be created successfully")
    public void thePolicyShouldBeCreatedSuccessfully() {
        assertEquals(HttpStatus.OK, lastResponse.getStatusCode());
        assertNotNull(lastResponse.getBody());
        assertNotNull(lastCreatedPolicyId);
    }

    @And("the policy should have the following details:")
    public void thePolicyShouldHaveTheFollowingDetails(DataTable dataTable) {
        Map<String, String> expectedData = dataTable.asMap(String.class, String.class);
        
        // Verify the policy was created by checking the database
        InsurancePolicy createdPolicy = policyRepository.findById(lastCreatedPolicyId).orElse(null);
        assertNotNull(createdPolicy);
        assertEquals(expectedData.get("name"), createdPolicy.getName());
        assertEquals(expectedData.get("status"), createdPolicy.getStatus().toString());
        assertEquals(LocalDate.parse(expectedData.get("startDate")), createdPolicy.getStartDate());
        assertEquals(LocalDate.parse(expectedData.get("endDate")), createdPolicy.getEndDate());
    }

    @Given("I have an existing insurance policy with name {string}")
    public void iHaveAnExistingInsurancePolicyWithName(String policyName) {
        InsurancePolicy policy = new InsurancePolicy();
        policy.setName(policyName);
        policy.setStatus(InsurancePolicy.Status.ACTIVE);
        policy.setStartDate(LocalDate.now());
        policy.setEndDate(LocalDate.now().plusYears(1));
        
        InsurancePolicy savedPolicy = policyRepository.save(policy);
        lastCreatedPolicyId = savedPolicy.getId();
    }

    @When("I retrieve the policy by its ID")
    public void iRetrieveThePolicyByItsID() {
        lastResponse = restTemplate.getForEntity("/api/v1/policies/" + lastCreatedPolicyId, ApiResponseDTO.class);
        
        if (lastResponse.getStatusCode() == HttpStatus.OK) {
            @SuppressWarnings("unchecked")
            ApiResponseDTO<PolicyDTO> responseBody = (ApiResponseDTO<PolicyDTO>) lastResponse.getBody();
            lastPolicyResponse = responseBody;
        }
    }

    @Then("I should get the policy details")
    public void iShouldGetThePolicyDetails() {
        assertEquals(HttpStatus.OK, lastResponse.getStatusCode());
        assertNotNull(lastResponse.getBody());
        assertNotNull(lastPolicyResponse);
        assertNotNull(lastPolicyResponse.getPayload());
    }

    @And("the policy name should be {string}")
    public void thePolicyNameShouldBe(String expectedName) {
        if (lastPolicyResponse != null && lastPolicyResponse.getPayload() != null) {
            assertEquals(expectedName, lastPolicyResponse.getPayload().getName());
        } else {
            // Fallback to database verification
            InsurancePolicy policy = policyRepository.findById(lastCreatedPolicyId).orElse(null);
            assertNotNull(policy);
            assertEquals(expectedName, policy.getName());
        }
    }

    @When("I update the policy with the following details:")
    public void iUpdateThePolicyWithTheFollowingDetails(DataTable dataTable) {
        Map<String, String> updateData = dataTable.asMap(String.class, String.class);
        
        PolicyUpdateDTO updateDTO = PolicyUpdateDTO.builder()
            .name(updateData.get("name"))
            .status(PolicyStatus.valueOf(updateData.get("status")))
            .build();
        
        ApiRequestDTO<PolicyUpdateDTO> requestDTO = new ApiRequestDTO<>(updateDTO);
        
        lastResponse = restTemplate.exchange(
            "/api/v1/policies/" + lastCreatedPolicyId,
            HttpMethod.PUT,
            new HttpEntity<>(requestDTO),
            ApiResponseDTO.class
        );
    }

    @Then("the policy should be updated successfully")
    public void thePolicyShouldBeUpdatedSuccessfully() {
        assertEquals(HttpStatus.OK, lastResponse.getStatusCode());
    }

    @And("the policy should have the updated details:")
    public void thePolicyShouldHaveTheUpdatedDetails(DataTable dataTable) {        Map<String, String> expectedData = dataTable.asMap(String.class, String.class);
        
        InsurancePolicy updatedPolicy = policyRepository.findById(lastCreatedPolicyId).orElse(null);
        assertNotNull(updatedPolicy);
        assertEquals(expectedData.get("name"), updatedPolicy.getName());
        assertEquals(expectedData.get("status"), updatedPolicy.getStatus().toString());
    }

    @When("I delete the policy")
    public void iDeleteThePolicy() {
        lastResponse = restTemplate.exchange(
            "/api/v1/policies/" + lastCreatedPolicyId,
            HttpMethod.DELETE,
            null,
            ApiResponseDTO.class
        );
    }

    @Then("the policy should be deleted successfully")
    public void thePolicyShouldBeDeletedSuccessfully() {
        assertEquals(HttpStatus.NO_CONTENT, lastResponse.getStatusCode());
    }

    @And("the policy should not exist in the system")
    public void thePolicyShouldNotExistInTheSystem() {
        assertFalse(policyRepository.existsById(lastCreatedPolicyId));
    }

    @Given("I have the following insurance policies:")
    public void iHaveTheFollowingInsurancePolicies(DataTable dataTable) {
        List<Map<String, String>> policies = dataTable.asMaps(String.class, String.class);
        
        for (Map<String, String> policyData : policies) {
            InsurancePolicy policy = new InsurancePolicy();
            policy.setName(policyData.get("name"));
            policy.setStatus(InsurancePolicy.Status.valueOf(policyData.get("status")));
            policy.setStartDate(LocalDate.parse(policyData.get("startDate")));
            policy.setEndDate(LocalDate.parse(policyData.get("endDate")));
            
            policyRepository.save(policy);
        }
    }

    @When("I request all policies")
    public void iRequestAllPolicies() {
        ParameterizedTypeReference<ApiResponseDTO<List<PolicyDTO>>> responseType = 
            new ParameterizedTypeReference<ApiResponseDTO<List<PolicyDTO>>>() {};
        
        lastResponse = restTemplate.exchange(
            "/api/v1/policies", 
            HttpMethod.GET, 
            null, 
            responseType
        );
    }

    @Then("I should get {int} policies")
    public void iShouldGetPolicies(int expectedCount) {
        assertEquals(HttpStatus.OK, lastResponse.getStatusCode());
        
        // Verify count in database
        long actualCount = policyRepository.count();
        assertEquals(expectedCount, actualCount);
        
        // Also verify count in response if available
        if (lastResponse.getBody() != null) {
            @SuppressWarnings("unchecked")
            ApiResponseDTO<List<PolicyDTO>> responseBody = (ApiResponseDTO<List<PolicyDTO>>) lastResponse.getBody();
            if (responseBody.getPayload() != null) {
                assertEquals(expectedCount, responseBody.getPayload().size());
            }
        }
    }

    @And("the policies should be returned in the correct format")
    public void thePoliciesShouldBeReturnedInTheCorrectFormat() {
        assertNotNull(lastResponse.getBody());
        
        @SuppressWarnings("unchecked")
        ApiResponseDTO<List<PolicyDTO>> responseBody = (ApiResponseDTO<List<PolicyDTO>>) lastResponse.getBody();
        assertNotNull(responseBody);
        assertNotNull(responseBody.getPayload());
        
        // Verify each policy has required fields
        for (PolicyDTO policy : responseBody.getPayload()) {
            assertNotNull(policy.getId());
            assertNotNull(policy.getName());
            assertNotNull(policy.getStatus());
            assertNotNull(policy.getStartDate());
            assertNotNull(policy.getEndDate());
        }
    }

    @When("I try to create a policy with the following invalid details:")
    public void iTryToCreateAPolicyWithTheFollowingInvalidDetails(DataTable dataTable) {
        Map<String, String> policyData = dataTable.asMap(String.class, String.class);
        
        PolicyCreateDTO createDTO = PolicyCreateDTO.builder()
            .name(policyData.get("name"))
            .status(PolicyStatus.valueOf(policyData.get("status")))
            .startDate(LocalDate.parse(policyData.get("startDate")))
            .endDate(LocalDate.parse(policyData.get("endDate")))
            .build();
        
        ApiRequestDTO<PolicyCreateDTO> requestDTO = new ApiRequestDTO<>(createDTO);
        
        lastResponse = restTemplate.postForEntity("/api/v1/policies", requestDTO, ApiResponseDTO.class);
        
        // Try to extract error message from response
        if (lastResponse.getBody() != null) {
            @SuppressWarnings("unchecked")
            ApiResponseDTO<Object> responseBody = (ApiResponseDTO<Object>) lastResponse.getBody();
            if (responseBody.getErrors() != null && !responseBody.getErrors().isEmpty()) {
                lastErrorMessage = responseBody.getErrors().get(0).getMessage();
            }
        }
    }

    @Then("I should get a validation error")
    public void iShouldGetAValidationError() {
        assertEquals(HttpStatus.BAD_REQUEST, lastResponse.getStatusCode());
        assertNotNull(lastResponse.getBody());
    }

    @And("the error should mention {string}")
    public void theErrorShouldMention(String expectedErrorMessage) {
        assertNotNull(lastErrorMessage);
        assertTrue(lastErrorMessage.contains(expectedErrorMessage) || 
                   lastErrorMessage.toLowerCase().contains(expectedErrorMessage.toLowerCase()),
                   "Expected error message to contain: " + expectedErrorMessage + 
                   ", but got: " + lastErrorMessage);
    }

    @When("I try to retrieve a policy with ID {int}")
    public void iTryToRetrieveAPolicyWithID(int policyId) {
        lastResponse = restTemplate.getForEntity("/api/v1/policies/" + policyId, ApiResponseDTO.class);
        
        // Try to extract error message from response
        if (lastResponse.getBody() != null) {
            @SuppressWarnings("unchecked")
            ApiResponseDTO<Object> responseBody = (ApiResponseDTO<Object>) lastResponse.getBody();
            if (responseBody.getErrors() != null && !responseBody.getErrors().isEmpty()) {
                lastErrorMessage = responseBody.getErrors().get(0).getMessage();
            }
        }
    }

    @Then("I should get a not found error")
    public void iShouldGetANotFoundError() {
        assertEquals(HttpStatus.NOT_FOUND, lastResponse.getStatusCode());
        assertNotNull(lastResponse.getBody());
    }

     */
}
