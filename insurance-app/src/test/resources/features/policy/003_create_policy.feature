Feature: Insurance Policy Creation
  As an insurance administrator
  I want to create insurance policies
  So that I can create and read policies effectively

  Background:
    Given the insurance policy application is running
    And the insurance policies database is clean

  Scenario: Create a new insurance policy
    When I create a new insurance policy with the following details:
      | name      | Auto Insurance Premium |
      | status    | ACTIVE                 |
      | startDate | 2024-01-01             |
      | endDate   | 2024-12-31             |
    Then the policy should be created successfully
    And the policy should have the following details:
      | name      | Auto Insurance Premium |
      | status    | ACTIVE                 |
      | startDate | 2024-01-01             |
      | endDate   | 2024-12-31             |
