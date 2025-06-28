package com.firstProject.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);
    private static final Properties properties = new Properties();
    private static ConfigManager instance;

    private ConfigManager() {
        loadProperties();
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                logger.error("Unable to find config.properties");
                throw new RuntimeException("config.properties not found in classpath");
            }
            properties.load(input);
            logger.info("Configuration properties loaded successfully");
        } catch (IOException e) {
            logger.error("Error loading configuration properties", e);
            throw new RuntimeException("Failed to load configuration properties", e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public int getIntProperty(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public int getIntProperty(String key, int defaultValue) {
        try {
            return Integer.parseInt(properties.getProperty(key));
        } catch (NumberFormatException e) {
            logger.warn("Could not parse property {} as integer, using default value: {}", key, defaultValue);
            return defaultValue;
        }
    }

    public boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }

    public boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
    }

    // Twitter API specific getters
    public String getTwitterApiBaseUrl() {
        return getProperty("twitter.api.base.url");
    }

    public String getTwitterApiV1Url() {
        return getProperty("twitter.api.v1.url");
    }

    public String getBearerToken() {
        return getProperty("twitter.bearer.token");
    }

    public String getApiKey() {
        return getProperty("twitter.api.key");
    }

    public String getApiSecret() {
        return getProperty("twitter.api.secret");
    }

    public String getAccessToken() {
        return getProperty("twitter.access.token");
    }

    public String getAccessTokenSecret() {
        return getProperty("twitter.access.token.secret");
    }

    public int getTestTimeout() {
        return getIntProperty("test.timeout", 30000);
    }

    public int getTestRetryCount() {
        return getIntProperty("test.retry.count", 3);
    }

    public int getParallelThreads() {
        return getIntProperty("test.parallel.threads", 3);
    }
} 