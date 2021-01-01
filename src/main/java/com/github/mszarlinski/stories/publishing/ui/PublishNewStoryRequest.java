package com.github.mszarlinski.stories.publishing.ui;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PublishNewStoryRequest(
        @JsonProperty("title") String title,
        @JsonProperty("content") String content) {
}
