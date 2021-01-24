package com.github.mszarlinski.stories.reading.domain;

import com.github.mszarlinski.stories.account.AccountModuleFacade;
import com.github.mszarlinski.stories.sharedkernel.StoryId;
import com.github.mszarlinski.stories.test.builder.TestStory;
import com.github.mszarlinski.stories.test.builder.TestUser;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.github.mszarlinski.stories.test.builder.TestStory.story;
import static com.github.mszarlinski.stories.test.builder.TestUser.user;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReadingUnitTests {

    static TestUser AUTHOR = user().build();

    AccountModuleFacade accountModuleFacadeMock = mock(AccountModuleFacade.class);

    StoryReaderFacade facade = new StoryReaderFacade(
            new StoryViewReadModel(new InMemoryStoryViewRepository()),
            accountModuleFacadeMock);

    @Test
    void shouldReturnStoriesForHomePage() {
        // given
        thereIsAnAuthor(AUTHOR);

        var story = story().withAuthorId(AUTHOR.getId()).build();
        thereIsAStory(story);

        // when
        var stories = facade.getStoriesForHomePage();

        // then
        assertThat(stories)
                .singleElement()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrProperty("id")
                .hasFieldOrPropertyWithValue("title", story.getTitle())
                .hasFieldOrPropertyWithValue("author", authorFullName())
                .hasFieldOrPropertyWithValue("publishedDate", story.getPublishedDate());
    }

    private void thereIsAnAuthor(TestUser author) {
        when(accountModuleFacadeMock.findAccountById(author.getId())).thenReturn(Optional.of(author.toUserDto()));
    }

    private void thereIsAStory(TestStory story) {
        facade.saveStoryInReadModels(StoryId.of(story.getId()), story.getTitle(), story.getContent(), story.getAuthorId(), story.getPublishedDate());
    }

    private String authorFullName() {
        return String.format("%s %s", AUTHOR.getName(), AUTHOR.getLastName());
    }

    @Test
    void shouldReturnStoryById() {
        // given
        thereIsAnAuthor(AUTHOR);
        var someStory = story().withAuthorId(AUTHOR.getId()).build();
        thereIsAStory(someStory);

        // when
        var story = facade.findStoryById(StoryId.of(someStory.getId()));

        // then
        assertThat(story)
                .hasValueSatisfying(s -> assertThat(s)
                        .hasFieldOrPropertyWithValue("title", someStory.getTitle())
                        .hasFieldOrPropertyWithValue("content", someStory.getContent())
                        .hasFieldOrPropertyWithValue("publishedDate", someStory.getPublishedDate())
                        .hasFieldOrPropertyWithValue("author", authorFullName()));
    }
}
