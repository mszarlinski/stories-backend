package com.github.mszarlinski.stories.publishing.domain;

import com.github.mszarlinski.stories.publishing.application.StoryDto;
import com.github.mszarlinski.stories.sharedkernel.StoryId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

import java.time.Instant;
import java.util.UUID;

public class PublishedStory {
    @Id
    private String id;
    private String title;
    private String authorId;
    private String content;
    private Instant publishedDate;

    @PersistenceCreator
    public PublishedStory(String title, String content, String authorId, Instant publishedDate) {
        this(UUID.randomUUID().toString(), title, content, authorId, publishedDate);
    }

    public PublishedStory(String id, String title, String content, String authorId, Instant publishedDate) {
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
