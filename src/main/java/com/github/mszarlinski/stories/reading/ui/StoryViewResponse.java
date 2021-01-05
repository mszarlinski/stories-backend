package com.github.mszarlinski.stories.reading.ui;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class StoryViewResponse {

    private final String title;
    private final String content;
    private final String author;
    private final Instant publishedDate;

    StoryViewResponse(@JsonProperty("title") String title,
                      @JsonProperty("content") String content,
                      @JsonProperty("author") String author,
                      @JsonProperty("publishedDate") Instant publishedDate) {

        this.title = title;
        this.content = content;
        this.author = author;
        this.publishedDate = publishedDate;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public Instant getPublishedDate() {
        return publishedDate;
    }
}
