package com.github.mszarlinski.stories.publishing.domain;

import com.github.mszarlinski.stories.common.StoryId;
import com.github.mszarlinski.stories.publishing.application.StoryDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import java.time.Instant;
import java.util.UUID;

public class PublishedStory {
    @Id
    private final String id;
    private final String title;
    private final String authorId;
    private final String content;
    private final Instant publishedDate;

    public PublishedStory(String title, String content, String authorId, Instant publishedDate) {
        this(UUID.randomUUID().toString(), title, content, authorId, publishedDate);
    }

    @PersistenceConstructor
    private PublishedStory(String id, String title, String content, String authorId, Instant publishedDate) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.content = content;
        this.publishedDate = publishedDate;
    }

    public StoryPublished storyPublishedEvent() {
        return new StoryPublished(
                getId(),
                title,
                content,
                authorId,
                publishedDate);
    }

    public StoryDto toDto() {
        return new StoryDto(getId(), title, content, authorId, publishedDate);
    }

    public StoryId getId() {
        return StoryId.of(id);
    }

    String getTitle() {
        return title;
    }

    String getAuthorId() {
        return authorId;
    }

    String getContent() {
        return content;
    }

    Instant getPublishedDate() {
        return publishedDate;
    }
}
