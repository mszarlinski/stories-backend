package com.github.mszarlinski.stories.reading.domain.homepage;

import java.time.Instant;

public class HomePageStoryView {
    private final String id;
    private final String title;
    private final String author;
    private final Instant publishedDate;

    HomePageStoryView(String id, String title, String author, Instant publishedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publishedDate = publishedDate;
    }

    public String getId() {
        return id;
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
