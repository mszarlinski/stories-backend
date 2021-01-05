package com.github.mszarlinski.stories.reading.domain.storyview;

import java.time.Instant;

public class StoryView {
    private final String id;
    private final String title;
    private final String author;
    private final String content;
    private final Instant publishedDate;

    StoryView(String id, String title, String author, String content, Instant publishedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public Instant getPublishedDate() {
        return publishedDate;
    }
}
