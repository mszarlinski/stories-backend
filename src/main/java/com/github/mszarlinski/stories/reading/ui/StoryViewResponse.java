package com.github.mszarlinski.stories.reading.ui;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record StoryViewResponse(
        @JsonProperty("title") String title,
        @JsonProperty("content") String content,
        @JsonProperty("author") String author,
        @JsonProperty("publishedDate") Instant publishedDate) {
}
