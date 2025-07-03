Feature: Simple Insurance Policy Searching
  As an insurance administrator
  I want to list insurance policies
  So that I can read policies effectively

  Background:
    Given the insurance policy application is running

  Scenario: List all insurance policies when DB is empty
    Given the insurance policies database is clean
    When I request all policies
    Then I should get 0 paginated policies with total of 0
