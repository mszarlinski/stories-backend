package com.github.mszarlinski.stories.publishing.ui;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PublishedStoryResponse {
    private final String id;
    private final String title;

    public PublishedStoryResponse(
            @JsonProperty("id") String id,
            @JsonProperty("title") String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
