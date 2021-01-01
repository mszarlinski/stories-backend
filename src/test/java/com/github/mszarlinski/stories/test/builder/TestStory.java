package com.github.mszarlinski.stories.test.builder;

import java.time.Instant;
import java.util.UUID;

public class TestStory {

    private final String id;
    private final String title;
    private final String content;
    private final String authorId;
    private final Instant publishedDate;

    TestStory(String id, String title, String content, String authorId, Instant publishedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.publishedDate = publishedDate;
    }

    public static TestStoryBuilder story() {
        return new TestStoryBuilder();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public String getAuthorId() {
        return this.authorId;
    }

    public Instant getPublishedDate() {
        return this.publishedDate;
    }

    public static class TestStoryBuilder {
        private String id = UUID.randomUUID().toString();
        private String title = "Some title";
        private String content = "Some content";
        private String authorId = "123";
        private Instant publishedDate = Instant.now();

        TestStoryBuilder() {
        }

        public TestStory.TestStoryBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public TestStory.TestStoryBuilder withContent(String content) {
            this.content = content;
            return this;
        }

        public TestStory.TestStoryBuilder withAuthorId(String authorId) {
            this.authorId = authorId;
            return this;
        }

        public TestStory.TestStoryBuilder withPublishedDate(Instant publishedDate) {
            this.publishedDate = publishedDate;
            return this;
        }

        public TestStory build() {
            return new TestStory(id, title, content, authorId, publishedDate);
        }
    }
}
