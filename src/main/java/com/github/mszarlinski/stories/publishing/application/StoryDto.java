package com.github.mszarlinski.stories.publishing.application;

import com.github.mszarlinski.stories.common.StoryId;

import java.time.Instant;

public class StoryDto {
    private final StoryId id;
    private final String title;
    private final String content;
    private final String authorId;
    private final Instant publishedDate;

    public StoryDto(StoryId id, String title, String content, String authorId, Instant publishedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.publishedDate = publishedDate;
    }

    public StoryId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthorId() {
        return authorId;
    }

    public Instant getPublishedDate() {
        return publishedDate;
    }
}
