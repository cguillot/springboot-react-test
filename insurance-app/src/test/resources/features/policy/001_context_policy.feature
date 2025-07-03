Feature: Application insurance policy context

  Scenario: Spring context loads and PolicyService is available with reachable DB
    Given the insurance policy application is running
    Then the insurance policy database is reachable