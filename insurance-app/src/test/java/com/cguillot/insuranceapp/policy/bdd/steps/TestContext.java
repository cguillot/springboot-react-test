package com.cguillot.insuranceapp.policy.bdd.steps;

import org.springframework.http.ResponseEntity;

public class TestContext {
    private ResponseEntity<?> latestResponse;
    // private ApiResponseDTO<PolicyDTO> lastPolicyResponse;
    // private String lastErrorMessage;

    public ResponseEntity<?> getLatestResponse() {
        return latestResponse;
    }

    public void setLatestResponse(ResponseEntity<?> response) {
        this.latestResponse = response;
    }
}
