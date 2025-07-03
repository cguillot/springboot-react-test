package com.cguillot.insuranceapp.policy.bdd.steps;

import com.cguillot.insuranceapp.policy.PolicyRepository;
import com.cguillot.insuranceapp.policy.PolicyService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ContextStepDefs {

    @Autowired
    private PolicyService policyService;

    @Autowired
    private PolicyRepository policyRepository;

    @Given("the insurance policy application is running")
    public void theInsurancePolicyApplicationIsRunning() {
        // No-op: if context didn't load, test would fail here
        assertNotNull(policyService, "PolicyService should be injected");
    }

    @Given("the insurance policies database is clean")
    public void theInsurancePoliciesDatabaseIsClean() {
        policyRepository.deleteAll();
    }

    @Then("the insurance policy database is reachable")
    public void theInsurancePolicyDatabaseIsReachable() {
        policyRepository.findAll();
    }
}
