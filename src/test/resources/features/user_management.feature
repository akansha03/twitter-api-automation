@user @api
Feature: Twitter User Management
  As a Twitter API user
  I want to manage user information
  So that I can retrieve and validate user data

  Background:
    Given I have valid Twitter API credentials
    And I am using the Twitter API v2

  @smoke @positive
  Scenario: Get user by username
    Given I want to get user information for username "twitter"
    When I send a GET request to get user by username
    Then the response status code should be 200
    And the response should contain user data
    And the username should be "twitter"
    And the response should contain required user fields

  @positive
  Scenario: Get user by ID
    Given I have a valid user ID "783214"
    When I send a GET request to get user by ID
    Then the response status code should be 200
    And the response should contain user data
    And the user ID should match "783214"
    And the response should contain required user fields

  @positive
  Scenario: Get multiple users by IDs
    Given I have multiple user IDs "783214,813286"
    When I send a GET request to get users by IDs
    Then the response status code should be 200
    And the response should contain user data for 2 users
    And each user should have required fields

  @negative
  Scenario: Get user with invalid username
    Given I want to get user information for invalid username "invalid_username_12345"
    When I send a GET request to get user by username
    Then the response status code should be 404
    And the response should contain error message

  @negative
  Scenario: Get user with invalid ID
    Given I have an invalid user ID "999999999999999999"
    When I send a GET request to get user by ID
    Then the response status code should be 404
    And the response should contain error message

  @performance
  Scenario: Verify response time for user lookup
    Given I want to get user information for username "twitter"
    When I send a GET request to get user by username
    Then the response time should be less than 5000 milliseconds
    And the response status code should be 200 