package com.firstProject.utils;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ResponseValidator {
    private static final Logger logger = LoggerFactory.getLogger(ResponseValidator.class);

    /**
     * Validate response status code
     */
    public static boolean validateStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        boolean isValid = actualStatusCode == expectedStatusCode;
        
        if (isValid) {
            logger.info("Status code validation passed: Expected {}, Got {}", expectedStatusCode, actualStatusCode);
        } else {
            logger.error("Status code validation failed: Expected {}, Got {}", expectedStatusCode, actualStatusCode);
        }
        
        return isValid;
    }

    /**
     * Validate response contains expected field
     */
    public static boolean validateFieldExists(Response response, String fieldPath) {
        try {
            Object value = response.jsonPath().get(fieldPath);
            boolean exists = value != null;
            
            if (exists) {
                logger.info("Field validation passed: Field '{}' exists with value: {}", fieldPath, value);
            } else {
                logger.error("Field validation failed: Field '{}' does not exist", fieldPath);
            }
            
            return exists;
        } catch (Exception e) {
            logger.error("Error validating field '{}': {}", fieldPath, e.getMessage());
            return false;
        }
    }

    /**
     * Validate response field value
     */
    public static boolean validateFieldValue(Response response, String fieldPath, Object expectedValue) {
        try {
            Object actualValue = response.jsonPath().get(fieldPath);
            boolean isValid = expectedValue.equals(actualValue);
            
            if (isValid) {
                logger.info("Field value validation passed: Field '{}' has expected value: {}", fieldPath, expectedValue);
            } else {
                logger.error("Field value validation failed: Field '{}' expected '{}', got '{}'", 
                    fieldPath, expectedValue, actualValue);
            }
            
            return isValid;
        } catch (Exception e) {
            logger.error("Error validating field value '{}': {}", fieldPath, e.getMessage());
            return false;
        }
    }

    /**
     * Validate response contains expected fields
     */
    public static boolean validateRequiredFields(Response response, String... requiredFields) {
        boolean allValid = true;
        
        for (String field : requiredFields) {
            if (!validateFieldExists(response, field)) {
                allValid = false;
            }
        }
        
        if (allValid) {
            logger.info("All required fields validation passed");
        } else {
            logger.error("Required fields validation failed");
        }
        
        return allValid;
    }

    /**
     * Validate response schema against JSON schema
     */
    public static boolean validateJsonSchema(Response response, String schemaPath) {
        try {
            response.then().assertThat().body(matchesJsonSchemaInClasspath(schemaPath));
            logger.info("JSON schema validation passed");
            return true;
        } catch (Exception e) {
            logger.error("JSON schema validation failed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Validate response time is within acceptable range
     */
    public static boolean validateResponseTime(Response response, long maxResponseTime) {
        long responseTime = response.getTime();
        boolean isValid = responseTime <= maxResponseTime;
        
        if (isValid) {
            logger.info("Response time validation passed: {}ms (max: {}ms)", responseTime, maxResponseTime);
        } else {
            logger.error("Response time validation failed: {}ms (max: {}ms)", responseTime, maxResponseTime);
        }
        
        return isValid;
    }

    /**
     * Validate response headers
     */
    public static boolean validateHeader(Response response, String headerName, String expectedValue) {
        String actualValue = response.getHeader(headerName);
        boolean isValid = expectedValue.equals(actualValue);
        
        if (isValid) {
            logger.info("Header validation passed: '{}' = '{}'", headerName, expectedValue);
        } else {
            logger.error("Header validation failed: '{}' expected '{}', got '{}'", 
                headerName, expectedValue, actualValue);
        }
        
        return isValid;
    }

    /**
     * Validate response contains error message
     */
    public static boolean validateErrorMessage(Response response, String expectedErrorMessage) {
        try {
            String actualErrorMessage = response.jsonPath().getString("errors[0].message");
            boolean isValid = expectedErrorMessage.equals(actualErrorMessage);
            
            if (isValid) {
                logger.info("Error message validation passed: '{}'", expectedErrorMessage);
            } else {
                logger.error("Error message validation failed: expected '{}', got '{}'", 
                    expectedErrorMessage, actualErrorMessage);
            }
            
            return isValid;
        } catch (Exception e) {
            logger.error("Error validating error message: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Validate response data count
     */
    public static boolean validateDataCount(Response response, String dataPath, int expectedCount) {
        try {
            Object data = response.jsonPath().get(dataPath);
            int actualCount = 0;
            
            if (data instanceof java.util.List) {
                actualCount = ((java.util.List<?>) data).size();
            } else if (data instanceof java.util.Map) {
                actualCount = ((java.util.Map<?, ?>) data).size();
            }
            
            boolean isValid = actualCount == expectedCount;
            
            if (isValid) {
                logger.info("Data count validation passed: {} items", expectedCount);
            } else {
                logger.error("Data count validation failed: expected {}, got {}", expectedCount, actualCount);
            }
            
            return isValid;
        } catch (Exception e) {
            logger.error("Error validating data count: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Comprehensive response validation
     */
    public static boolean validateResponse(Response response, int expectedStatusCode, 
                                         String[] requiredFields, long maxResponseTime) {
        boolean isValid = true;
        
        // Validate status code
        if (!validateStatusCode(response, expectedStatusCode)) {
            isValid = false;
        }
        
        // Validate required fields
        if (requiredFields != null && requiredFields.length > 0) {
            if (!validateRequiredFields(response, requiredFields)) {
                isValid = false;
            }
        }
        
        // Validate response time
        if (!validateResponseTime(response, maxResponseTime)) {
            isValid = false;
        }
        
        return isValid;
    }

    /**
     * Log response details for debugging
     */
    public static void logResponseDetails(Response response) {
        logger.info("Response Details:");
        logger.info("Status Code: {}", response.getStatusCode());
        logger.info("Status Line: {}", response.getStatusLine());
        logger.info("Response Time: {}ms", response.getTime());
        logger.info("Content Type: {}", response.getContentType());
        logger.info("Headers: {}", response.getHeaders());
        logger.info("Body: {}", response.getBody().asString());
    }
} 