package com.firstProject.api;

import com.firstProject.config.ConfigManager;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class TwitterApiClient extends BaseApiClient {
    private static final Logger logger = LoggerFactory.getLogger(TwitterApiClient.class);

    // Twitter API v2 Endpoints
    private static final String USERS_ENDPOINT = "/users";
    private static final String TWEETS_ENDPOINT = "/tweets";
    private static final String SEARCH_ENDPOINT = "/tweets/search/recent";
    private static final String USER_TIMELINE_ENDPOINT = "/users/{id}/tweets";
    private static final String USER_MENTIONS_ENDPOINT = "/users/{id}/mentions";
    private static final String USER_FOLLOWERS_ENDPOINT = "/users/{id}/followers";
    private static final String USER_FOLLOWING_ENDPOINT = "/users/{id}/following";

    // Twitter API v1.1 Endpoints
    private static final String V1_STATUSES_UPDATE = "/statuses/update.json";
    private static final String V1_STATUSES_DESTROY = "/statuses/destroy/{id}.json";
    private static final String V1_FRIENDSHIPS_CREATE = "/friendships/create.json";
    private static final String V1_FRIENDSHIPS_DESTROY = "/friendships/destroy.json";

    public TwitterApiClient() {
        super();
    }

    // User Management Methods
    public Response getUserById(String userId) {
        String endpoint = buildUrl(USERS_ENDPOINT, userId);
        logRequest("GET", endpoint, null);
        
        Response response = getRequestSpec()
                .when()
                .get(endpoint)
                .then()
                .spec(responseSpec)
                .extract().response();
        
        logResponse(response.getStatusCode(), response.getBody().asString());
        return response;
    }

    public Response getUserByUsername(String username) {
        String endpoint = buildQueryUrl(USERS_ENDPOINT + "/by/username", "username", username);
        logRequest("GET", endpoint, null);
        
        Response response = getRequestSpec()
                .when()
                .get(endpoint)
                .then()
                .spec(responseSpec)
                .extract().response();
        
        logResponse(response.getStatusCode(), response.getBody().asString());
        return response;
    }

    public Response getUsersByIds(String... userIds) {
        String userIdsParam = String.join(",", userIds);
        String endpoint = buildQueryUrl(USERS_ENDPOINT, "ids", userIdsParam);
        logRequest("GET", endpoint, null);
        
        Response response = getRequestSpec()
                .when()
                .get(endpoint)
                .then()
                .spec(responseSpec)
                .extract().response();
        
        logResponse(response.getStatusCode(), response.getBody().asString());
        return response;
    }

    // Tweet Management Methods
    public Response getTweetById(String tweetId) {
        String endpoint = buildUrl(TWEETS_ENDPOINT, tweetId);
        logRequest("GET", endpoint, null);
        
        Response response = getRequestSpec()
                .when()
                .get(endpoint)
                .then()
                .spec(responseSpec)
                .extract().response();
        
        logResponse(response.getStatusCode(), response.getBody().asString());
        return response;
    }

    public Response getTweetsByIds(String... tweetIds) {
        String tweetIdsParam = String.join(",", tweetIds);
        String endpoint = buildQueryUrl(TWEETS_ENDPOINT, "ids", tweetIdsParam);
        logRequest("GET", endpoint, null);
        
        Response response = getRequestSpec()
                .when()
                .get(endpoint)
                .then()
                .spec(responseSpec)
                .extract().response();
        
        logResponse(response.getStatusCode(), response.getBody().asString());
        return response;
    }

    public Response searchRecentTweets(String query, Map<String, String> params) {
        StringBuilder endpoint = new StringBuilder(SEARCH_ENDPOINT);
        if (params != null && !params.isEmpty()) {
            endpoint.append("?");
            boolean first = true;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (!first) endpoint.append("&");
                endpoint.append(entry.getKey()).append("=").append(entry.getValue());
                first = false;
            }
        }
        
        logRequest("GET", endpoint.toString(), null);
        
        Response response = getRequestSpec()
                .when()
                .get(endpoint.toString())
                .then()
                .spec(responseSpec)
                .extract().response();
        
        logResponse(response.getStatusCode(), response.getBody().asString());
        return response;
    }

    public Response getUserTimeline(String userId, Map<String, String> params) {
        String endpoint = USER_TIMELINE_ENDPOINT.replace("{id}", userId);
        if (params != null && !params.isEmpty()) {
            endpoint += "?";
            boolean first = true;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (!first) endpoint += "&";
                endpoint += entry.getKey() + "=" + entry.getValue();
                first = false;
            }
        }
        
        logRequest("GET", endpoint, null);
        
        Response response = getRequestSpec()
                .when()
                .get(endpoint)
                .then()
                .spec(responseSpec)
                .extract().response();
        
        logResponse(response.getStatusCode(), response.getBody().asString());
        return response;
    }

    public Response getUserMentions(String userId, Map<String, String> params) {
        String endpoint = USER_MENTIONS_ENDPOINT.replace("{id}", userId);
        if (params != null && !params.isEmpty()) {
            endpoint += "?";
            boolean first = true;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (!first) endpoint += "&";
                endpoint += entry.getKey() + "=" + entry.getValue();
                first = false;
            }
        }
        
        logRequest("GET", endpoint, null);
        
        Response response = getRequestSpec()
                .when()
                .get(endpoint)
                .then()
                .spec(responseSpec)
                .extract().response();
        
        logResponse(response.getStatusCode(), response.getBody().asString());
        return response;
    }

    // Followers/Following Methods
    public Response getUserFollowers(String userId, Map<String, String> params) {
        String endpoint = USER_FOLLOWERS_ENDPOINT.replace("{id}", userId);
        if (params != null && !params.isEmpty()) {
            endpoint += "?";
            boolean first = true;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (!first) endpoint += "&";
                endpoint += entry.getKey() + "=" + entry.getValue();
                first = false;
            }
        }
        
        logRequest("GET", endpoint, null);
        
        Response response = getRequestSpec()
                .when()
                .get(endpoint)
                .then()
                .spec(responseSpec)
                .extract().response();
        
        logResponse(response.getStatusCode(), response.getBody().asString());
        return response;
    }

    public Response getUserFollowing(String userId, Map<String, String> params) {
        String endpoint = USER_FOLLOWING_ENDPOINT.replace("{id}", userId);
        if (params != null && !params.isEmpty()) {
            endpoint += "?";
            boolean first = true;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (!first) endpoint += "&";
                endpoint += entry.getKey() + "=" + entry.getValue();
                first = false;
            }
        }
        
        logRequest("GET", endpoint, null);
        
        Response response = getRequestSpec()
                .when()
                .get(endpoint)
                .then()
                .spec(responseSpec)
                .extract().response();
        
        logResponse(response.getStatusCode(), response.getBody().asString());
        return response;
    }

    // v1.1 API Methods (for actions that require OAuth 1.0a)
    public Response createTweet(String text) {
        logRequest("POST", V1_STATUSES_UPDATE, text);
        
        Response response = getRequestSpecV1()
                .param("status", text)
                .when()
                .post(V1_STATUSES_UPDATE)
                .then()
                .spec(responseSpec)
                .extract().response();
        
        logResponse(response.getStatusCode(), response.getBody().asString());
        return response;
    }

    public Response deleteTweet(String tweetId) {
        String endpoint = V1_STATUSES_DESTROY.replace("{id}", tweetId);
        logRequest("POST", endpoint, null);
        
        Response response = getRequestSpecV1()
                .when()
                .post(endpoint)
                .then()
                .spec(responseSpec)
                .extract().response();
        
        logResponse(response.getStatusCode(), response.getBody().asString());
        return response;
    }

    public Response followUser(String userId) {
        logRequest("POST", V1_FRIENDSHIPS_CREATE, userId);
        
        Response response = getRequestSpecV1()
                .param("user_id", userId)
                .when()
                .post(V1_FRIENDSHIPS_CREATE)
                .then()
                .spec(responseSpec)
                .extract().response();
        
        logResponse(response.getStatusCode(), response.getBody().asString());
        return response;
    }

    public Response unfollowUser(String userId) {
        logRequest("POST", V1_FRIENDSHIPS_DESTROY, userId);
        
        Response response = getRequestSpecV1()
                .param("user_id", userId)
                .when()
                .post(V1_FRIENDSHIPS_DESTROY)
                .then()
                .spec(responseSpec)
                .extract().response();
        
        logResponse(response.getStatusCode(), response.getBody().asString());
        return response;
    }
} 