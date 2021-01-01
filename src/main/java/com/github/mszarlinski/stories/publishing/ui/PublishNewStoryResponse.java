package com.github.mszarlinski.stories.publishing.ui;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PublishNewStoryResponse(@JsonProperty("storyId") String storyId) {
}
