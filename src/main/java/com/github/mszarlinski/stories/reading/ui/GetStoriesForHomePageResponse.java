package com.github.mszarlinski.stories.reading.ui;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

public record GetStoriesForHomePageResponse(@JsonProperty("stories") List<HomePageStoryViewResponse> stories) {
}

record HomePageStoryViewResponse(@JsonProperty("title") String title,
                                 @JsonProperty("author") String author,
                                 @JsonProperty("publishedDate") Instant publishedDate) {
}
