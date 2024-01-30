Feature: REST API basics

  Scenario: Get data
    Given the API endpoint is "{{base_url}}/info?id=1"
    When the "GET" request is sent
    Then the response code should be "200"
  Scenario: Post data
    Given the API endpoint is "{{base_url}}/info"
    When the "POST" request is sent
    Then the response code should be "200"
  Scenario: Update data
    Given the API endpoint is "{{base_url}}/info?id=1"
    When the "PUT" request is sent
    Then the response code should be "200"
  Scenario: Delete data
    Given the API endpoint is "{{base_url}}/info?id=1"
    When the "DELETE" request is sent
    Then the response code should be "200"
  Scenario: YT
    Given the API endpoint is "https://www.youtube.com/watch?v=mKHIGIdFgBw"
    When the "GET" request is sent
    Then the response code should be "200"
     And the response should contain "Chittiveri"
