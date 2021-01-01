package com.github.mszarlinski.stories.publishing.application;

import com.github.mszarlinski.stories.common.StoryId;

import java.time.Instant;

public record StoryDto(StoryId id, String title, String content, String authorId, Instant publishedDate) {
}
