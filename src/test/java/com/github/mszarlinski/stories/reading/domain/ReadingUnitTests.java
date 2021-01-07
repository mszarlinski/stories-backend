package com.github.mszarlinski.stories.reading.domain;

import com.github.mszarlinski.stories.account.AccountModuleFacade;
import com.github.mszarlinski.stories.account.UserDto;
import com.github.mszarlinski.stories.reading.domain.homepage.ReaderHomePageReadModel;
import com.github.mszarlinski.stories.reading.domain.storyview.StoryViewReadModel;
import com.github.mszarlinski.stories.sharedkernel.StoryId;
import com.github.mszarlinski.stories.test.builder.TestStory;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.github.mszarlinski.stories.test.builder.TestStory.story;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReadingUnitTests {

    static UserDto AUTHOR = new UserDto("Janusz", "Kowalski");

    AccountModuleFacade accountModuleFacadeMock = mock(AccountModuleFacade.class);

    StoryReaderFacade facade = new StoryReaderFacade(
            new ReaderHomePageReadModel(new InMemoryHomePageStoryRepository()),
            new StoryViewReadModel(new InMemoryStoryViewRepository()),
            accountModuleFacadeMock);

    @Test
    void shouldReturnStoriesForHomePage() {
        // given
        var story = story().build();
        thereIsAStory(story);

        // when
        var stories = facade.getStoriesForHomePage();

        // then
        assertThat(stories)
                .singleElement()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrProperty("id")
                .hasFieldOrPropertyWithValue("title", story.getTitle())
                .hasFieldOrPropertyWithValue("author", "Janusz Kowalski")
                .hasFieldOrPropertyWithValue("publishedDate", story.getPublishedDate());
    }


    private void thereIsAnAuthor(UserDto author, String authorId) {
        when(accountModuleFacadeMock.getUserById(authorId)).thenReturn(Optional.of(author));
    }

    private void thereIsAStory(TestStory story) {
        thereIsAnAuthor(AUTHOR, story.getAuthorId());
        facade.saveStoryInReadModels(StoryId.of(story.getId()), story.getTitle(), story.getContent(), story.getAuthorId(), story.getPublishedDate());
    }

    @Test
    void shouldReturnStoryById() {
        // given
        var someStory = story().build();
        thereIsAStory(someStory);

        // when
        var story = facade.findStoryById(StoryId.of(someStory.getId()));

        // then
        assertThat(story)
                .hasValueSatisfying(s -> assertThat(s)
                        .hasFieldOrPropertyWithValue("title", someStory.getTitle())
                        .hasFieldOrPropertyWithValue("content", someStory.getContent())
                        .hasFieldOrPropertyWithValue("publishedDate", someStory.getPublishedDate())
                        .hasFieldOrPropertyWithValue("author", "Janusz Kowalski"));
    }
}
