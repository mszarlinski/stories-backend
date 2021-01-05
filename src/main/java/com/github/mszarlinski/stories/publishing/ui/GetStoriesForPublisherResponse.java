package com.github.mszarlinski.stories.publishing.ui;

import com.fasterxml.jackson.annotation.JsonProperty;

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
