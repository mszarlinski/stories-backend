package com.github.mszarlinski.stories.reading;

import com.github.mszarlinski.stories.AcceptanceTests;
import com.github.mszarlinski.stories.publishing.ui.PublishNewStoryRequest;
import com.github.mszarlinski.stories.publishing.ui.PublishNewStoryResponse;
import com.github.mszarlinski.stories.reading.ui.GetStoriesForHomePageResponse;
import com.github.mszarlinski.stories.reading.ui.StoryViewResponse;
import com.github.mszarlinski.stories.test.builder.TestStory;
import org.junit.jupiter.api.Test;

import static com.github.mszarlinski.stories.account.AccountModuleFacade.FAKE_USER;
import static com.github.mszarlinski.stories.test.builder.TestStory.story;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

class ReadingAcceptanceTests extends AcceptanceTests {

    @Test
    void shouldReturnStoriesForHomePage() {
        // given
        var story = story().build();
        storyIsPublished(story);

        // when
        var storiesResponse = client.getForEntity("/home/stories", GetStoriesForHomePageResponse.class);

        // then
        assertThat(storiesResponse.getStatusCode()).isEqualTo(OK);
        assertThat(storiesResponse.getBody().stories())
                .singleElement()
                .hasFieldOrPropertyWithValue("title", story.getTitle())
                .hasFieldOrPropertyWithValue("author", "%s %s".formatted(FAKE_USER.name(), FAKE_USER.lastName()))
                .hasFieldOrPropertyWithValue("publishedDate", clock.instant());
    }

    private String storyIsPublished(TestStory story) {
        return client.postForObject("/stories", new PublishNewStoryRequest(story.getTitle(), story.getContent()), PublishNewStoryResponse.class)
                .storyId();
    }

    @Test
    void shouldReturnStoryById() {
        // given
        var story = story().build();
        var storyId = storyIsPublished(story);

        // when
        var storyResponse = client.getForEntity("/stories/{storyId}", StoryViewResponse.class, storyId);

        // then
        assertThat(storyResponse.getStatusCode()).isEqualTo(OK);
        assertThat(storyResponse.getBody())
                .hasFieldOrPropertyWithValue("title", story.getTitle())
                .hasFieldOrPropertyWithValue("content", story.getContent())
                .hasFieldOrPropertyWithValue("publishedDate", clock.instant())
                .hasFieldOrPropertyWithValue("author", "%s %s".formatted(FAKE_USER.name(), FAKE_USER.lastName()));
    }

    @Test
    void shouldReturn404IfStoryDoesNotExist() {
        // given
        var notExistingStoryId = "1234";
        // when
        var storyResponse = client.getForEntity("/stories/${storyId}", GetStoriesForHomePageResponse.class, notExistingStoryId);

        // then
        assertThat(storyResponse.getStatusCode()).isEqualTo(NOT_FOUND);
    }

    @Test
    void shouldReturnMaxNStoriesSortedByPublishedDate() {
//TODO
    }

    @Test
    void shouldGetStoryBySlug() {
//TODO
    }
}
