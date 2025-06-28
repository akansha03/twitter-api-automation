package com.firstProject.stepdefinitions;

import com.firstProject.api.TwitterApiClient;
import com.firstProject.config.ConfigManager;
import com.firstProject.utils.ResponseValidator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class UserStepDefinitions {
    private static final Logger logger = LoggerFactory.getLogger(UserStepDefinitions.class);
    
    private TwitterApiClient twitterApiClient;
    private Response response;
    private String username;
    private String userId;
    private String[] userIds;
    private ConfigManager config;

    public UserStepDefinitions() {
        this.twitterApiClient = new TwitterApiClient();
        this.config = ConfigManager.getInstance();
    }

    @Given("I have valid Twitter API credentials")
    public void i_have_valid_twitter_api_credentials() {
        logger.info("Setting up Twitter API credentials");
        Assert.assertNotNull(config.getBearerToken(), "Bearer token should not be null");
        Assert.assertNotNull(config.getTwitterApiBaseUrl(), "API base URL should not be null");
    }

    @Given("I am using the Twitter API v2")
    public void i_am_using_the_twitter_api_v2() {
        logger.info("Using Twitter API v2: {}", config.getTwitterApiBaseUrl());
    }

    @Given("I want to get user information for username {string}")
    public void i_want_to_get_user_information_for_username(String username) {
        this.username = username;
        logger.info("Preparing to get user information for username: {}", username);
    }

    @Given("I have a valid user ID {string}")
    public void i_have_a_valid_user_id(String userId) {
        this.userId = userId;
        logger.info("Preparing to get user information for user ID: {}", userId);
    }

    @Given("I have multiple user IDs {string}")
    public void i_have_multiple_user_ids(String userIdsString) {
        this.userIds = userIdsString.split(",");
        logger.info("Preparing to get user information for user IDs: {}", userIdsString);
    }

    @Given("I want to get user information for invalid username {string}")
    public void i_want_to_get_user_information_for_invalid_username(String username) {
        this.username = username;
        logger.info("Preparing to test invalid username: {}", username);
    }

    @Given("I have an invalid user ID {string}")
    public void i_have_an_invalid_user_id(String userId) {
        this.userId = userId;
        logger.info("Preparing to test invalid user ID: {}", userId);
    }

    @When("I send a GET request to get user by username")
    public void i_send_a_get_request_to_get_user_by_username() {
        logger.info("Sending GET request to get user by username: {}", username);
        response = twitterApiClient.getUserByUsername(username);
    }

    @When("I send a GET request to get user by ID")
    public void i_send_a_get_request_to_get_user_by_id() {
        logger.info("Sending GET request to get user by ID: {}", userId);
        response = twitterApiClient.getUserById(userId);
    }

    @When("I send a GET request to get users by IDs")
    public void i_send_a_get_request_to_get_users_by_ids() {
        logger.info("Sending GET request to get users by IDs");
        response = twitterApiClient.getUsersByIds(userIds);
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int expectedStatusCode) {
        logger.info("Validating response status code: expected {}, actual {}", expectedStatusCode, response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, 
            "Response status code should be " + expectedStatusCode);
    }

    @Then("the response should contain user data")
    public void the_response_should_contain_user_data() {
        logger.info("Validating response contains user data");
        String responseBody = response.getBody().asString();
        Assert.assertNotNull(responseBody, "Response body should not be null");
        Assert.assertTrue(responseBody.contains("data"), "Response should contain 'data' field");
    }

    @Then("the username should be {string}")
    public void the_username_should_be(String expectedUsername) {
        logger.info("Validating username: expected {}, actual {}", expectedUsername, username);
        String actualUsername = response.jsonPath().getString("data.username");
        Assert.assertEquals(actualUsername, expectedUsername, "Username should match");
    }

    @Then("the user ID should match {string}")
    public void the_user_id_should_match(String expectedUserId) {
        logger.info("Validating user ID: expected {}, actual {}", expectedUserId, userId);
        String actualUserId = response.jsonPath().getString("data.id");
        Assert.assertEquals(actualUserId, expectedUserId, "User ID should match");
    }

    @Then("the response should contain required user fields")
    public void the_response_should_contain_required_user_fields() {
        logger.info("Validating required user fields");
        String[] requiredFields = {"id", "name", "username"};
        
        for (String field : requiredFields) {
            String value = response.jsonPath().getString("data." + field);
            Assert.assertNotNull(value, "Required field '" + field + "' should not be null");
            Assert.assertFalse(value.isEmpty(), "Required field '" + field + "' should not be empty");
        }
    }

    @Then("the response should contain user data for {int} users")
    public void the_response_should_contain_user_data_for_users(int expectedCount) {
        logger.info("Validating user data count: expected {}, actual {}", expectedCount, 
            response.jsonPath().getList("data").size());
        int actualCount = response.jsonPath().getList("data").size();
        Assert.assertEquals(actualCount, expectedCount, "User count should match");
    }

    @Then("each user should have required fields")
    public void each_user_should_have_required_fields() {
        logger.info("Validating required fields for each user");
        String[] requiredFields = {"id", "name", "username"};
        
        for (int i = 0; i < response.jsonPath().getList("data").size(); i++) {
            for (String field : requiredFields) {
                String value = response.jsonPath().getString("data[" + i + "]." + field);
                Assert.assertNotNull(value, "User " + i + " required field '" + field + "' should not be null");
                Assert.assertFalse(value.isEmpty(), "User " + i + " required field '" + field + "' should not be empty");
            }
        }
    }

    @Then("the response should contain error message")
    public void the_response_should_contain_error_message() {
        logger.info("Validating error message in response");
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("errors") || responseBody.contains("error"), 
            "Response should contain error information");
    }

    @Then("the response time should be less than {long} milliseconds")
    public void the_response_time_should_be_less_than_milliseconds(long maxResponseTime) {
        logger.info("Validating response time: actual {}ms, max {}ms", response.getTime(), maxResponseTime);
        Assert.assertTrue(response.getTime() <= maxResponseTime, 
            "Response time should be less than " + maxResponseTime + "ms");
    }
} 