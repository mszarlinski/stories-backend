package com.github.mszarlinski.stories.reading.ui;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

public class GetStoriesForHomePageResponse {

    private final List<HomePageStoryViewResponse> stories;

    GetStoriesForHomePageResponse(@JsonProperty("stories") List<HomePageStoryViewResponse> stories) {
        this.stories = stories;
    }

    public List<HomePageStoryViewResponse> getStories() {
        return stories;
    }
}

class HomePageStoryViewResponse {

    private final String title;
    private final String author;
    private final Instant publishedDate;

    public HomePageStoryViewResponse(@JsonProperty("title") String title,
                                     @JsonProperty("author") String author,
                                     @JsonProperty("publishedDate") Instant publishedDate) {
        this.title = title;
        this.author = author;
        this.publishedDate = publishedDate;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Instant getPublishedDate() {
        return publishedDate;
    }
}
