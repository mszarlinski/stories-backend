package com.github.mszarlinski.stories.reading.domain.storyview;

import java.time.Instant;

public record StoryView(String id, String title, String author, String content, Instant publishedDate) {
}
