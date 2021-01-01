package com.github.mszarlinski.stories.publishing.ui;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GetStoriesForPublisherResponse(
        @JsonProperty("stories") List<PublishedStoryResponse> stories) {
}
