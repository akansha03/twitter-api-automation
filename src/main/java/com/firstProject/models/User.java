package com.firstProject.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("username")
    private String username;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("location")
    private String location;
    
    @JsonProperty("url")
    private String url;
    
    @JsonProperty("protected")
    private Boolean isProtected;
    
    @JsonProperty("verified")
    private Boolean verified;
    
    @JsonProperty("followers_count")
    private Integer followersCount;
    
    @JsonProperty("following_count")
    private Integer followingCount;
    
    @JsonProperty("tweet_count")
    private Integer tweetCount;
    
    @JsonProperty("listed_count")
    private Integer listedCount;
    
    @JsonProperty("created_at")
    private String createdAt;
    
    @JsonProperty("profile_image_url")
    private String profileImageUrl;
    
    @JsonProperty("profile_banner_url")
    private String profileBannerUrl;

    // Constructors
    public User() {}

    public User(String id, String name, String username) {
        this.id = id;
        this.name = name;
        this.username = username;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getIsProtected() {
        return isProtected;
    }

    public void setIsProtected(Boolean isProtected) {
        this.isProtected = isProtected;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Integer getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    public Integer getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(Integer followingCount) {
        this.followingCount = followingCount;
    }

    public Integer getTweetCount() {
        return tweetCount;
    }

    public void setTweetCount(Integer tweetCount) {
        this.tweetCount = tweetCount;
    }

    public Integer getListedCount() {
        return listedCount;
    }

    public void setListedCount(Integer listedCount) {
        this.listedCount = listedCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getProfileBannerUrl() {
        return profileBannerUrl;
    }

    public void setProfileBannerUrl(String profileBannerUrl) {
        this.profileBannerUrl = profileBannerUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", url='" + url + '\'' +
                ", isProtected=" + isProtected +
                ", verified=" + verified +
                ", followersCount=" + followersCount +
                ", followingCount=" + followingCount +
                ", tweetCount=" + tweetCount +
                ", listedCount=" + listedCount +
                ", createdAt='" + createdAt + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", profileBannerUrl='" + profileBannerUrl + '\'' +
                '}';
    }
} 