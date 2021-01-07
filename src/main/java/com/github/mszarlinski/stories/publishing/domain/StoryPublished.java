package com.github.mszarlinski.stories.publishing.domain;

import com.github.mszarlinski.stories.sharedkernel.StoryId;
import com.github.mszarlinski.stories.sharedkernel.event.DomainEvent;

import java.time.Instant;

public class StoryPublished implements DomainEvent {
    private final StoryId storyId;
    private final String title;
    private final String content;
    private final String authorId;
    private final Instant publishedDate;

    //TODO stringly typed ;(
    StoryPublished(StoryId storyId, String title, String content, String authorId, Instant publishedDate) {
        this.storyId = storyId;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.publishedDate = publishedDate;
    }

    public StoryId getStoryId() {
        return storyId;
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
