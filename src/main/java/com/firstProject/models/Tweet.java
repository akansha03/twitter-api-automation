package com.firstProject.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Tweet {
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("text")
    private String text;
    
    @JsonProperty("author_id")
    private String authorId;
    
    @JsonProperty("created_at")
    private String createdAt;
    
    @JsonProperty("conversation_id")
    private String conversationId;
    
    @JsonProperty("in_reply_to_user_id")
    private String inReplyToUserId;
    
    @JsonProperty("referenced_tweets")
    private List<ReferencedTweet> referencedTweets;
    
    @JsonProperty("public_metrics")
    private PublicMetrics publicMetrics;
    
    @JsonProperty("entities")
    private Entities entities;
    
    @JsonProperty("context_annotations")
    private List<ContextAnnotation> contextAnnotations;
    
    @JsonProperty("lang")
    private String lang;
    
    @JsonProperty("possibly_sensitive")
    private Boolean possiblySensitive;
    
    @JsonProperty("source")
    private String source;

    // Constructors
    public Tweet() {}

    public Tweet(String id, String text, String authorId) {
        this.id = id;
        this.text = text;
        this.authorId = authorId;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getInReplyToUserId() {
        return inReplyToUserId;
    }

    public void setInReplyToUserId(String inReplyToUserId) {
        this.inReplyToUserId = inReplyToUserId;
    }

    public List<ReferencedTweet> getReferencedTweets() {
        return referencedTweets;
    }

    public void setReferencedTweets(List<ReferencedTweet> referencedTweets) {
        this.referencedTweets = referencedTweets;
    }

    public PublicMetrics getPublicMetrics() {
        return publicMetrics;
    }

    public void setPublicMetrics(PublicMetrics publicMetrics) {
        this.publicMetrics = publicMetrics;
    }

    public Entities getEntities() {
        return entities;
    }

    public void setEntities(Entities entities) {
        this.entities = entities;
    }

    public List<ContextAnnotation> getContextAnnotations() {
        return contextAnnotations;
    }

    public void setContextAnnotations(List<ContextAnnotation> contextAnnotations) {
        this.contextAnnotations = contextAnnotations;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Boolean getPossiblySensitive() {
        return possiblySensitive;
    }

    public void setPossiblySensitive(Boolean possiblySensitive) {
        this.possiblySensitive = possiblySensitive;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", authorId='" + authorId + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", conversationId='" + conversationId + '\'' +
                ", inReplyToUserId='" + inReplyToUserId + '\'' +
                ", referencedTweets=" + referencedTweets +
                ", publicMetrics=" + publicMetrics +
                ", entities=" + entities +
                ", contextAnnotations=" + contextAnnotations +
                ", lang='" + lang + '\'' +
                ", possiblySensitive=" + possiblySensitive +
                ", source='" + source + '\'' +
                '}';
    }

    // Inner classes for nested objects
    public static class ReferencedTweet {
        @JsonProperty("type")
        private String type;
        
        @JsonProperty("id")
        private String id;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class PublicMetrics {
        @JsonProperty("retweet_count")
        private Integer retweetCount;
        
        @JsonProperty("reply_count")
        private Integer replyCount;
        
        @JsonProperty("like_count")
        private Integer likeCount;
        
        @JsonProperty("quote_count")
        private Integer quoteCount;

        public Integer getRetweetCount() {
            return retweetCount;
        }

        public void setRetweetCount(Integer retweetCount) {
            this.retweetCount = retweetCount;
        }

        public Integer getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(Integer replyCount) {
            this.replyCount = replyCount;
        }

        public Integer getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(Integer likeCount) {
            this.likeCount = likeCount;
        }

        public Integer getQuoteCount() {
            return quoteCount;
        }

        public void setQuoteCount(Integer quoteCount) {
            this.quoteCount = quoteCount;
        }
    }

    public static class Entities {
        @JsonProperty("urls")
        private List<UrlEntity> urls;
        
        @JsonProperty("hashtags")
        private List<HashtagEntity> hashtags;
        
        @JsonProperty("mentions")
        private List<MentionEntity> mentions;

        public List<UrlEntity> getUrls() {
            return urls;
        }

        public void setUrls(List<UrlEntity> urls) {
            this.urls = urls;
        }

        public List<HashtagEntity> getHashtags() {
            return hashtags;
        }

        public void setHashtags(List<HashtagEntity> hashtags) {
            this.hashtags = hashtags;
        }

        public List<MentionEntity> getMentions() {
            return mentions;
        }

        public void setMentions(List<MentionEntity> mentions) {
            this.mentions = mentions;
        }
    }

    public static class UrlEntity {
        @JsonProperty("url")
        private String url;
        
        @JsonProperty("expanded_url")
        private String expandedUrl;
        
        @JsonProperty("display_url")
        private String displayUrl;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getExpandedUrl() {
            return expandedUrl;
        }

        public void setExpandedUrl(String expandedUrl) {
            this.expandedUrl = expandedUrl;
        }

        public String getDisplayUrl() {
            return displayUrl;
        }

        public void setDisplayUrl(String displayUrl) {
            this.displayUrl = displayUrl;
        }
    }

    public static class HashtagEntity {
        @JsonProperty("tag")
        private String tag;

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }
    }

    public static class MentionEntity {
        @JsonProperty("username")
        private String username;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    public static class ContextAnnotation {
        @JsonProperty("domain")
        private Domain domain;
        
        @JsonProperty("entity")
        private Entity entity;

        public Domain getDomain() {
            return domain;
        }

        public void setDomain(Domain domain) {
            this.domain = domain;
        }

        public Entity getEntity() {
            return entity;
        }

        public void setEntity(Entity entity) {
            this.entity = entity;
        }
    }

    public static class Domain {
        @JsonProperty("id")
        private String id;
        
        @JsonProperty("name")
        private String name;

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
    }

    public static class Entity {
        @JsonProperty("id")
        private String id;
        
        @JsonProperty("name")
        private String name;

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
    }
} 