package com.firstProject.hooks;

import com.firstProject.config.ConfigManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);
    private final ConfigManager config = ConfigManager.getInstance();

    @Before
    public void setUp(Scenario scenario) {
        logger.info("Starting scenario: {}", scenario.getName());
        logger.info("Scenario tags: {}", scenario.getSourceTagNames());
        
        // Initialize any test data or configuration
        logger.info("Test configuration loaded - Base URL: {}", config.getTwitterApiBaseUrl());
        logger.info("Test timeout: {}ms", config.getTestTimeout());
    }

    @After
    public void tearDown(Scenario scenario) {
        logger.info("Finishing scenario: {}", scenario.getName());
        
        if (scenario.isFailed()) {
            logger.error("Scenario failed: {}", scenario.getName());
            // Add any cleanup logic for failed scenarios
        } else {
            logger.info("Scenario passed: {}", scenario.getName());
        }
        
        // Clean up any test data or resources
        logger.info("Scenario cleanup completed");
    }

    @Before("@api")
    public void setUpApiTests() {
        logger.info("Setting up API test environment");
        // Add API-specific setup logic
    }

    @After("@api")
    public void tearDownApiTests() {
        logger.info("Cleaning up API test environment");
        // Add API-specific cleanup logic
    }

    @Before("@user")
    public void setUpUserTests() {
        logger.info("Setting up user-related test environment");
        // Add user-specific setup logic
    }

    @After("@user")
    public void tearDownUserTests() {
        logger.info("Cleaning up user-related test environment");
        // Add user-specific cleanup logic
    }

    @Before("@tweet")
    public void setUpTweetTests() {
        logger.info("Setting up tweet-related test environment");
        // Add tweet-specific setup logic
    }

    @After("@tweet")
    public void tearDownTweetTests() {
        logger.info("Cleaning up tweet-related test environment");
        // Add tweet-specific cleanup logic
    }
} 