package com.firstProject.stepdefinitions;

import com.firstProject.api.TwitterApiClient;
import com.firstProject.config.ConfigManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class TweetStepDefinitions {
    private static final Logger logger = LoggerFactory.getLogger(TweetStepDefinitions.class);
    
    private TwitterApiClient twitterApiClient;
    private Response response;
    private String tweetId;
    private String[] tweetIds;
    private String searchQuery;
    private String tweetText;
    private String userId;
    private ConfigManager config;

    public TweetStepDefinitions() {
        this.twitterApiClient = new TwitterApiClient();
        this.config = ConfigManager.getInstance();
    }

    @Given("I have a valid tweet ID {string}")
    public void i_have_a_valid_tweet_id(String tweetId) {
        this.tweetId = tweetId;
        logger.info("Preparing to get tweet information for tweet ID: {}", tweetId);
    }

    @Given("I have multiple tweet IDs {string}")
    public void i_have_multiple_tweet_ids(String tweetIdsString) {
        this.tweetIds = tweetIdsString.split(",");
        logger.info("Preparing to get tweet information for tweet IDs: {}", tweetIdsString);
    }

    @Given("I want to search for tweets with query {string}")
    public void i_want_to_search_for_tweets_with_query(String query) {
        this.searchQuery = query;
        logger.info("Preparing to search for tweets with query: {}", query);
    }

    @Given("I have a valid user ID {string}")
    public void i_have_a_valid_user_id(String userId) {
        this.userId = userId;
        logger.info("Preparing to work with user ID: {}", userId);
    }

    @Given("I want to get the user's timeline")
    public void i_want_to_get_the_user_s_timeline() {
        logger.info("Preparing to get timeline for user ID: {}", userId);
    }

    @Given("I want to get the user's mentions")
    public void i_want_to_get_the_user_s_mentions() {
        logger.info("Preparing to get mentions for user ID: {}", userId);
    }

    @Given("I have an invalid tweet ID {string}")
    public void i_have_an_invalid_tweet_id(String tweetId) {
        this.tweetId = tweetId;
        logger.info("Preparing to test invalid tweet ID: {}", tweetId);
    }

    @Given("I want to search for tweets with empty query")
    public void i_want_to_search_for_tweets_with_empty_query() {
        this.searchQuery = "";
        logger.info("Preparing to test search with empty query");
    }

    @Given("I want to create a new tweet with text {string}")
    public void i_want_to_create_a_new_tweet_with_text(String tweetText) {
        this.tweetText = tweetText;
        logger.info("Preparing to create tweet with text: {}", tweetText);
    }

    @Given("I have a tweet ID to delete {string}")
    public void i_have_a_tweet_id_to_delete(String tweetId) {
        this.tweetId = tweetId;
        logger.info("Preparing to delete tweet with ID: {}", tweetId);
    }

    @When("I send a GET request to get tweet by ID")
    public void i_send_a_get_request_to_get_tweet_by_id() {
        logger.info("Sending GET request to get tweet by ID: {}", tweetId);
        response = twitterApiClient.getTweetById(tweetId);
    }

    @When("I send a GET request to get tweets by IDs")
    public void i_send_a_get_request_to_get_tweets_by_ids() {
        logger.info("Sending GET request to get tweets by IDs");
        response = twitterApiClient.getTweetsByIds(tweetIds);
    }

    @When("I send a GET request to search recent tweets")
    public void i_send_a_get_request_to_search_recent_tweets() {
        logger.info("Sending GET request to search recent tweets with query: {}", searchQuery);
        Map<String, String> params = new HashMap<>();
        if (searchQuery != null && !searchQuery.isEmpty()) {
            params.put("query", searchQuery);
        }
        response = twitterApiClient.searchRecentTweets(searchQuery, params);
    }

    @When("I send a GET request to get user timeline")
    public void i_send_a_get_request_to_get_user_timeline() {
        logger.info("Sending GET request to get user timeline for user ID: {}", userId);
        Map<String, String> params = new HashMap<>();
        params.put("max_results", "10");
        response = twitterApiClient.getUserTimeline(userId, params);
    }

    @When("I send a GET request to get user mentions")
    public void i_send_a_get_request_to_get_user_mentions() {
        logger.info("Sending GET request to get user mentions for user ID: {}", userId);
        Map<String, String> params = new HashMap<>();
        params.put("max_results", "10");
        response = twitterApiClient.getUserMentions(userId, params);
    }

    @When("I send a POST request to create tweet")
    public void i_send_a_post_request_to_create_tweet() {
        logger.info("Sending POST request to create tweet with text: {}", tweetText);
        response = twitterApiClient.createTweet(tweetText);
    }

    @When("I send a POST request to delete tweet")
    public void i_send_a_post_request_to_delete_tweet() {
        logger.info("Sending POST request to delete tweet with ID: {}", tweetId);
        response = twitterApiClient.deleteTweet(tweetId);
    }

    @Then("the response should contain tweet data")
    public void the_response_should_contain_tweet_data() {
        logger.info("Validating response contains tweet data");
        String responseBody = response.getBody().asString();
        Assert.assertNotNull(responseBody, "Response body should not be null");
        Assert.assertTrue(responseBody.contains("data"), "Response should contain 'data' field");
    }

    @Then("the tweet ID should match {string}")
    public void the_tweet_id_should_match(String expectedTweetId) {
        logger.info("Validating tweet ID: expected {}, actual {}", expectedTweetId, tweetId);
        String actualTweetId = response.jsonPath().getString("data.id");
        Assert.assertEquals(actualTweetId, expectedTweetId, "Tweet ID should match");
    }

    @Then("the response should contain required tweet fields")
    public void the_response_should_contain_required_tweet_fields() {
        logger.info("Validating required tweet fields");
        String[] requiredFields = {"id", "text"};
        
        for (String field : requiredFields) {
            String value = response.jsonPath().getString("data." + field);
            Assert.assertNotNull(value, "Required field '" + field + "' should not be null");
            Assert.assertFalse(value.isEmpty(), "Required field '" + field + "' should not be empty");
        }
    }

    @Then("the response should contain tweet data for {int} tweets")
    public void the_response_should_contain_tweet_data_for_tweets(int expectedCount) {
        logger.info("Validating tweet data count: expected {}, actual {}", expectedCount, 
            response.jsonPath().getList("data").size());
        int actualCount = response.jsonPath().getList("data").size();
        Assert.assertEquals(actualCount, expectedCount, "Tweet count should match");
    }

    @Then("each tweet should have required fields")
    public void each_tweet_should_have_required_fields() {
        logger.info("Validating required fields for each tweet");
        String[] requiredFields = {"id", "text"};
        
        for (int i = 0; i < response.jsonPath().getList("data").size(); i++) {
            for (String field : requiredFields) {
                String value = response.jsonPath().getString("data[" + i + "]." + field);
                Assert.assertNotNull(value, "Tweet " + i + " required field '" + field + "' should not be null");
                Assert.assertFalse(value.isEmpty(), "Tweet " + i + " required field '" + field + "' should not be empty");
            }
        }
    }

    @Then("the response should contain search results")
    public void the_response_should_contain_search_results() {
        logger.info("Validating search results in response");
        String responseBody = response.getBody().asString();
        Assert.assertNotNull(responseBody, "Response body should not be null");
        Assert.assertTrue(responseBody.contains("data") || responseBody.contains("meta"), 
            "Response should contain search results");
    }

    @Then("the search results should contain tweet data")
    public void the_search_results_should_contain_tweet_data() {
        logger.info("Validating search results contain tweet data");
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("data"), "Search results should contain tweet data");
    }

    @Then("the response should contain timeline data")
    public void the_response_should_contain_timeline_data() {
        logger.info("Validating timeline data in response");
        String responseBody = response.getBody().asString();
        Assert.assertNotNull(responseBody, "Response body should not be null");
        Assert.assertTrue(responseBody.contains("data") || responseBody.contains("meta"), 
            "Response should contain timeline data");
    }

    @Then("the timeline should contain tweet data")
    public void the_timeline_should_contain_tweet_data() {
        logger.info("Validating timeline contains tweet data");
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("data"), "Timeline should contain tweet data");
    }

    @Then("the response should contain mentions data")
    public void the_response_should_contain_mentions_data() {
        logger.info("Validating mentions data in response");
        String responseBody = response.getBody().asString();
        Assert.assertNotNull(responseBody, "Response body should not be null");
        Assert.assertTrue(responseBody.contains("data") || responseBody.contains("meta"), 
            "Response should contain mentions data");
    }

    @Then("the mentions should contain tweet data")
    public void the_mentions_should_contain_tweet_data() {
        logger.info("Validating mentions contain tweet data");
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("data"), "Mentions should contain tweet data");
    }

    @Then("the tweet text should match {string}")
    public void the_tweet_text_should_match(String expectedText) {
        logger.info("Validating tweet text: expected {}, actual {}", expectedText, tweetText);
        String actualText = response.jsonPath().getString("text");
        Assert.assertEquals(actualText, expectedText, "Tweet text should match");
    }

    @Then("the response should contain deletion confirmation")
    public void the_response_should_contain_deletion_confirmation() {
        logger.info("Validating deletion confirmation in response");
        String responseBody = response.getBody().asString();
        Assert.assertNotNull(responseBody, "Response body should not be null");
        Assert.assertTrue(responseBody.contains("id"), "Response should contain deletion confirmation");
    }
} 