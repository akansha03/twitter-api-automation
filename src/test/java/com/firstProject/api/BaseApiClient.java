package com.firstProject.api;

import com.firstProject.config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.TimeUnit;
import static org.hamcrest.Matchers.lessThan;

public class BaseApiClient {
    private static final Logger logger = LoggerFactory.getLogger(BaseApiClient.class);
    protected final ConfigManager config;
    protected RequestSpecification requestSpec;
    protected ResponseSpecification responseSpec;

    public BaseApiClient() {
        this.config = ConfigManager.getInstance();
        initializeSpecifications();
    }

    private void initializeSpecifications() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(config.getTwitterApiBaseUrl())
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + config.getBearerToken())
                .addHeader("User-Agent", "Twitter-API-Automation/1.0")
                .log(LogDetail.ALL)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan((long) config.getTestTimeout()), TimeUnit.MILLISECONDS)
                .log(LogDetail.ALL)
                .build();
    }

    protected RequestSpecification getRequestSpec() {
        return RestAssured.given().spec(requestSpec);
    }

    protected RequestSpecification getRequestSpecV1() {
        return RestAssured.given()
                .spec(requestSpec)
                .baseUri(config.getTwitterApiV1Url());
    }

    protected void logRequest(String method, String endpoint, Object body) {
        logger.info("Making {} request to endpoint: {}", method, endpoint);
        if (body != null) {
            logger.debug("Request body: {}", body);
        }
    }

    protected void logResponse(int statusCode, String responseBody) {
        logger.info("Response status code: {}", statusCode);
        logger.debug("Response body: {}", responseBody);
    }

    protected void validateResponse(int expectedStatusCode, String responseBody) {
        if (responseBody == null || responseBody.isEmpty()) {
            logger.warn("Response body is empty");
        }
        // Additional validation logic can be added here
    }

    protected String buildUrl(String endpoint, String... pathParams) {
        StringBuilder url = new StringBuilder(endpoint);
        for (String param : pathParams) {
            url.append("/").append(param);
        }
        return url.toString();
    }

    protected String buildQueryUrl(String endpoint, String queryParam, String value) {
        return endpoint + "?" + queryParam + "=" + value;
    }

    protected String buildQueryUrl(String endpoint, String... queryParams) {
        StringBuilder url = new StringBuilder(endpoint);
        if (queryParams.length > 0) {
            url.append("?");
            for (int i = 0; i < queryParams.length; i += 2) {
                if (i > 0) url.append("&");
                url.append(queryParams[i]).append("=").append(queryParams[i + 1]);
            }
        }
        return url.toString();
    }
} 