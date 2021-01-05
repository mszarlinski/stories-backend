package com.github.mszarlinski.stories.publishing.ui;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PublishNewStoryResponse {
    private final String storyId;

    PublishNewStoryResponse(@JsonProperty("storyId") String storyId) {
        this.storyId = storyId;
    }

    public String getStoryId() {
        return storyId;
    }
}
