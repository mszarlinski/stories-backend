package com.github.mszarlinski.stories.publishing.domain;

import com.github.mszarlinski.stories.publishing.application.StoryPublisherFacade;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static com.github.mszarlinski.stories.test.builder.TestStory.story;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PublishingTestsClaudeCode {

    RecordingEventsPublisher eventsPublisher = new RecordingEventsPublisher();
    Clock clock = Clock.fixed(Instant.now(), ZoneOffset.UTC);
    StoryPublisherFacade facade = new StoryPublisherFacade(
            new InMemoryPublishedStoryRepository(),
            clock,
            eventsPublisher
    );

    @Test
    void shouldRejectNullTitle() {
        // given
        var story = story().withTitle(null).build();

        // when & then
        assertThatThrownBy(() -> facade.publish(story.getTitle(), story.getContent(), story.getAuthorId()))
                .isInstanceOf(EmptyTitleException.class)
                .hasMessage("Story title cannot be empty or blank");
    }

    @Test
    void shouldRejectEmptyTitle() {
        // given
        var story = story().withTitle("").build();

        // when & then
        assertThatThrownBy(() -> facade.publish(story.getTitle(), story.getContent(), story.getAuthorId()))
                .isInstanceOf(EmptyTitleException.class)
                .hasMessage("Story title cannot be empty or blank");
    }

    @Test
    void shouldRejectBlankTitle() {
        // given
        var story = story().withTitle("   \t\n  ").build();

        // when & then
        assertThatThrownBy(() -> facade.publish(story.getTitle(), story.getContent(), story.getAuthorId()))
                .isInstanceOf(EmptyTitleException.class)
                .hasMessage("Story title cannot be empty or blank");
    }

    @Test
    void shouldRejectNullContent() {
        // given
        var story = story().withContent(null).build();

        // when & then
        assertThatThrownBy(() -> facade.publish(story.getTitle(), story.getContent(), story.getAuthorId()))
                .isInstanceOf(EmptyContentException.class)
                .hasMessage("Story content cannot be empty or blank");
    }

    @Test
    void shouldRejectEmptyContent() {
        // given
        var story = story().withContent("").build();

        // when & then
        assertThatThrownBy(() -> facade.publish(story.getTitle(), story.getContent(), story.getAuthorId()))
                .isInstanceOf(EmptyContentException.class)
                .hasMessage("Story content cannot be empty or blank");
    }

    @Test
    void shouldRejectBlankContent() {
        // given
        var story = story().withContent("   \t\n  ").build();

        // when & then
        assertThatThrownBy(() -> facade.publish(story.getTitle(), story.getContent(), story.getAuthorId()))
                .isInstanceOf(EmptyContentException.class)
                .hasMessage("Story content cannot be empty or blank");
    }

    @Test
    void shouldRejectNullAuthorId() {
        // given
        var story = story().withAuthorId(null).build();

        // when & then
        assertThatThrownBy(() -> facade.publish(story.getTitle(), story.getContent(), story.getAuthorId()))
                .isInstanceOf(InvalidAuthorException.class)
                .hasMessage("Author information is required to publish a story");
    }

    @Test
    void shouldRejectEmptyAuthorId() {
        // given
        var story = story().withAuthorId("").build();

        // when & then
        assertThatThrownBy(() -> facade.publish(story.getTitle(), story.getContent(), story.getAuthorId()))
                .isInstanceOf(InvalidAuthorException.class)
                .hasMessage("Author information is required to publish a story");
    }

    @Test
    void shouldRejectBlankAuthorId() {
        // given
        var story = story().withAuthorId("   \t\n  ").build();

        // when & then
        assertThatThrownBy(() -> facade.publish(story.getTitle(), story.getContent(), story.getAuthorId()))
                .isInstanceOf(InvalidAuthorException.class)
                .hasMessage("Author information is required to publish a story");
    }

    @Test
    void shouldHandleLongTitle() {
        // given
        var longTitle = "A".repeat(1000);
        var story = story().withTitle(longTitle).build();

        // when
        facade.publish(story.getTitle(), story.getContent(), story.getAuthorId());

        // then
        var stories = facade.getStories(story.getAuthorId());
        assertThat(stories)
                .singleElement()
                .hasFieldOrPropertyWithValue("title", longTitle)
                .hasFieldOrPropertyWithValue("content", story.getContent());
    }

    @Test
    void shouldHandleLongContent() {
        // given
        var longContent = "Lorem ipsum ".repeat(10000);
        var story = story().withContent(longContent).build();

        // when
        facade.publish(story.getTitle(), story.getContent(), story.getAuthorId());

        // then
        var stories = facade.getStories(story.getAuthorId());
        assertThat(stories)
                .singleElement()
                .hasFieldOrPropertyWithValue("title", story.getTitle())
                .hasFieldOrPropertyWithValue("content", longContent);
    }

    @Test
    void shouldHandleSpecialCharactersInTitle() {
        // given
        var specialTitle = "Title with special chars: áéíóú ñ ç €$£¥ @#%&*";
        var story = story().withTitle(specialTitle).build();

        // when
        facade.publish(story.getTitle(), story.getContent(), story.getAuthorId());

        // then
        var stories = facade.getStories(story.getAuthorId());
        assertThat(stories)
                .singleElement()
                .hasFieldOrPropertyWithValue("title", specialTitle);
    }

    @Test
    void shouldHandleSpecialCharactersInContent() {
        // given
        var specialContent = "Content with special chars: áéíóú ñ ç €$£¥ @#%&*\nNew line\tTab";
        var story = story().withContent(specialContent).build();

        // when
        facade.publish(story.getTitle(), story.getContent(), story.getAuthorId());

        // then
        var stories = facade.getStories(story.getAuthorId());
        assertThat(stories)
                .singleElement()
                .hasFieldOrPropertyWithValue("content", specialContent);
    }

    @Test
    void shouldPublishMultipleStoriesBySameAuthor() {
        // given
        var story1 = story().withTitle("Title 1").withContent("Content 1").build();
        var story2 = story().withTitle("Title 2").withContent("Content 2").withAuthorId(story1.getAuthorId()).build();

        // when
        facade.publish(story1.getTitle(), story1.getContent(), story1.getAuthorId());
        facade.publish(story2.getTitle(), story2.getContent(), story2.getAuthorId());

        // then
        var stories = facade.getStories(story1.getAuthorId());
        assertThat(stories)
                .hasSize(2)
                .extracting("title")
                .containsExactlyInAnyOrder("Title 1", "Title 2");
    }

    @Test
    void shouldPublishStoriesByDifferentAuthors() {
        // given
        var story1 = story().withTitle("Title 1").withAuthorId("author1").build();
        var story2 = story().withTitle("Title 2").withAuthorId("author2").build();

        // when
        facade.publish(story1.getTitle(), story1.getContent(), story1.getAuthorId());
        facade.publish(story2.getTitle(), story2.getContent(), story2.getAuthorId());

        // then
        var storiesAuthor1 = facade.getStories("author1");
        var storiesAuthor2 = facade.getStories("author2");

        assertThat(storiesAuthor1)
                .singleElement()
                .hasFieldOrPropertyWithValue("title", "Title 1")
                .hasFieldOrPropertyWithValue("authorId", "author1");

        assertThat(storiesAuthor2)
                .singleElement()
                .hasFieldOrPropertyWithValue("title", "Title 2")
                .hasFieldOrPropertyWithValue("authorId", "author2");
    }

    @Test
    void shouldReturnEmptyListForNonExistentAuthor() {
        // given
        var story = story().build();
        facade.publish(story.getTitle(), story.getContent(), story.getAuthorId());

        // when
        var stories = facade.getStories("non-existent-author");

        // then
        assertThat(stories).isEmpty();
    }

    @Test
    void shouldGenerateUniqueStoryIds() {
        // given
        var story = story().build();

        // when
        var storyId1 = facade.publish(story.getTitle(), story.getContent(), story.getAuthorId());
        var storyId2 = facade.publish(story.getTitle(), story.getContent(), story.getAuthorId());

        // then
        assertThat(storyId1).isNotEqualTo(storyId2);

        var stories = facade.getStories(story.getAuthorId());
        assertThat(stories)
                .hasSize(2)
                .extracting("id")
                .doesNotHaveDuplicates();
    }

    @Test
    void shouldEmitEventForEachPublishedStory() {
        // given
        var story1 = story().withTitle("Title 1").build();
        var story2 = story().withTitle("Title 2").build();

        // when
        facade.publish(story1.getTitle(), story1.getContent(), story1.getAuthorId());
        facade.publish(story2.getTitle(), story2.getContent(), story2.getAuthorId());

        // then
        assertThat(eventsPublisher.events())
                .hasSize(2)
                .extracting("title")
                .containsExactly("Title 1", "Title 2");
    }

    @Test
    void shouldUseClockForPublishedDate() {
        // given
        var fixedTime = Instant.parse("2023-01-01T12:00:00Z");
        var fixedClock = Clock.fixed(fixedTime, ZoneOffset.UTC);
        var facadeWithFixedClock = new StoryPublisherFacade(
                new InMemoryPublishedStoryRepository(),
                fixedClock,
                eventsPublisher
        );
        var story = story().build();

        // when
        facadeWithFixedClock.publish(story.getTitle(), story.getContent(), story.getAuthorId());

        // then
        var stories = facadeWithFixedClock.getStories(story.getAuthorId());
        assertThat(stories)
                .singleElement()
                .hasFieldOrPropertyWithValue("publishedDate", fixedTime);
    }
}