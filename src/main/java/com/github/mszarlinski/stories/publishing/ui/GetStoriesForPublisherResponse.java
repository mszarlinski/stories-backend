package com.github.mszarlinski.stories.publishing.ui;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

public class GetStoriesForPublisherResponse {
    private final List<PublishedStoryResponse> stories;

    GetStoriesForPublisherResponse(
            @JsonProperty("stories") List<PublishedStoryResponse> stories) {
        this.stories = stories;
    }

    public List<PublishedStoryResponse> getStories() {
        return stories;
    }
}

class PublishedStoryResponse {
    private final String id;
    private final String title;
    private final Instant publishedDate;

    public PublishedStoryResponse(
            @JsonProperty("id") String id,
            @JsonProperty("title") String title,
            @JsonProperty("publishedDate") Instant publishedDate) {
        this.id = id;
        this.title = title;
        this.publishedDate = publishedDate;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Instant getPublishedDate() {
        return publishedDate;
    }
}
