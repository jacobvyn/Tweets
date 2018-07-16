package com.jacob.twitter.data.service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "tweets"})
public class TwitterResponse {
    @JsonProperty("tweets")
    List<Tweet> tweets;

    @JsonProperty("tweets")

    public List<Tweet> getTweets() {
        return tweets;
    }

    @JsonProperty("tweets")

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }
}