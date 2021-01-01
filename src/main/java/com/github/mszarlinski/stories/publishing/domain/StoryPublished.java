package com.github.mszarlinski.stories.publishing.domain;

import com.github.mszarlinski.stories.common.StoryId;
import com.github.mszarlinski.stories.sharedkernel.event.DomainEvent;

import java.time.Instant;

//TODO stringly typed ;(
public record StoryPublished(StoryId storyId, String title, String content, String authorId, Instant publishedDate) implements DomainEvent {
}
