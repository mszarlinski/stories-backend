package com.github.mszarlinski.stories.publishing.domain;

import com.github.mszarlinski.stories.publishing.application.StoryPublisherFacade;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static com.github.mszarlinski.stories.test.builder.TestStory.story;
import static org.assertj.core.api.Assertions.assertThat;

class PublishingUnitTests {

    RecordingEventsPublisher eventsPublisher = new RecordingEventsPublisher();

    Clock clock = Clock.fixed(Instant.now(), ZoneOffset.UTC);

    StoryPublisherFacade facade = new StoryPublisherFacade(
            new InMemoryPublishedStoryRepository(),
            clock,
            eventsPublisher
    );

    @Test
    void shouldSavePublishedStory() {
        // given
        var story = story().build();

        // when
        facade.publish(story.getTitle(), story.getContent(), story.getAuthorId());

        // then
        var stories = facade.getStories(story.getAuthorId());
        assertThat(stories)
                .singleElement()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrProperty("id")
                .hasFieldOrPropertyWithValue("title", story.getTitle())
                .hasFieldOrPropertyWithValue("content", story.getContent())
                .hasFieldOrPropertyWithValue("authorId", story.getAuthorId())
                .hasFieldOrPropertyWithValue("publishedDate", clock.instant());
    }

    @Test
    void shouldEmitStoryPublishedEvent() {
        // given
        var story = story().build();

        // when
        var storyId = facade.publish(story.getTitle(), story.getContent(), story.getAuthorId());

        // then
        assertThat(eventsPublisher.events())
                .singleElement()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("storyId", storyId)
                .hasFieldOrPropertyWithValue("title", story.getTitle())
                .hasFieldOrPropertyWithValue("content", story.getContent())
                .hasFieldOrPropertyWithValue("authorId", story.getAuthorId())
                .hasFieldOrPropertyWithValue("publishedDate", clock.instant());
    }
}
