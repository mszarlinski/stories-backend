package com.github.mszarlinski.stories.publishing.ui;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PublishedStoryResponse(
        @JsonProperty("id") String id,
        @JsonProperty("title") String title) {
}
