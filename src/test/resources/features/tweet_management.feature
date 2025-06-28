@tweet @api
Feature: Twitter Tweet Management
  As a Twitter API user
  I want to manage tweet information
  So that I can retrieve and validate tweet data

  Background:
    Given I have valid Twitter API credentials
    And I am using the Twitter API v2

  @smoke @positive
  Scenario: Get tweet by ID
    Given I have a valid tweet ID "1234567890123456789"
    When I send a GET request to get tweet by ID
    Then the response status code should be 200
    And the response should contain tweet data
    And the tweet ID should match "1234567890123456789"
    And the response should contain required tweet fields

  @positive
  Scenario: Get multiple tweets by IDs
    Given I have multiple tweet IDs "1234567890123456789,1234567890123456790"
    When I send a GET request to get tweets by IDs
    Then the response status code should be 200
    And the response should contain tweet data for 2 tweets
    And each tweet should have required fields

  @positive
  Scenario: Search recent tweets
    Given I want to search for tweets with query "API testing"
    When I send a GET request to search recent tweets
    Then the response status code should be 200
    And the response should contain search results
    And the search results should contain tweet data

  @positive
  Scenario: Get user timeline
    Given I have a valid user ID "783214"
    And I want to get the user's timeline
    When I send a GET request to get user timeline
    Then the response status code should be 200
    And the response should contain timeline data
    And the timeline should contain tweet data

  @positive
  Scenario: Get user mentions
    Given I have a valid user ID "783214"
    And I want to get the user's mentions
    When I send a GET request to get user mentions
    Then the response status code should be 200
    And the response should contain mentions data
    And the mentions should contain tweet data

  @negative
  Scenario: Get tweet with invalid ID
    Given I have an invalid tweet ID "9999999999999999999"
    When I send a GET request to get tweet by ID
    Then the response status code should be 404
    And the response should contain error message

  @negative
  Scenario: Search with empty query
    Given I want to search for tweets with empty query
    When I send a GET request to search recent tweets
    Then the response status code should be 400
    And the response should contain error message

  @performance
  Scenario: Verify response time for tweet lookup
    Given I have a valid tweet ID "1234567890123456789"
    When I send a GET request to get tweet by ID
    Then the response time should be less than 5000 milliseconds
    And the response status code should be 200

  @v1_api
  Scenario: Create a new tweet
    Given I want to create a new tweet with text "This is a test tweet from API automation"
    When I send a POST request to create tweet
    Then the response status code should be 200
    And the response should contain tweet data
    And the tweet text should match "This is a test tweet from API automation"

  @v1_api
  Scenario: Delete a tweet
    Given I have a tweet ID to delete "1234567890123456789"
    When I send a POST request to delete tweet
    Then the response status code should be 200
    And the response should contain deletion confirmation 