package com.github.mszarlinski.stories.reading;

import com.github.mszarlinski.stories.AcceptanceTests;
import com.github.mszarlinski.stories.account.AccountModuleFacade;
import com.github.mszarlinski.stories.account.FindOrCreateAccountCommand;
import com.github.mszarlinski.stories.account.UserDto;
import com.github.mszarlinski.stories.publishing.ui.PublishNewStoryRequest;
import com.github.mszarlinski.stories.publishing.ui.PublishNewStoryResponse;
import com.github.mszarlinski.stories.reading.ui.GetStoriesForHomePageResponse;
import com.github.mszarlinski.stories.reading.ui.StoryViewResponse;
import com.github.mszarlinski.stories.test.builder.TestStory;
import com.github.mszarlinski.stories.test.builder.TestUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.mszarlinski.stories.test.SecurityUtils.MOCK_TOKEN;
import static com.github.mszarlinski.stories.test.SecurityUtils.authorized;
import static com.github.mszarlinski.stories.test.builder.TestStory.story;
import static com.github.mszarlinski.stories.test.builder.TestUser.user;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

class ReadingAcceptanceTests extends AcceptanceTests {

    @Autowired
    AccountModuleFacade accountModuleFacade;

    @Test
    void shouldReturnStoriesForHomePage() {
        // given
        var author = user().build();
        thereIsAnAuthor(author);
        fakeJwtDecoder.mockUser(author, MOCK_TOKEN);

        var story = story().build();
        var storyId = storyIsPublished(story);

        await().atMost(1, SECONDS).untilAsserted(() -> {
            // when
            var storiesResponse = client
                    .getForEntity("/public/home/stories", GetStoriesForHomePageResponse.class);

            // then
            assertThat(storiesResponse.getStatusCode()).isEqualTo(OK);
            assertThat(storiesResponse.getBody().getStories())
                    .singleElement()
                    .hasNoNullFieldsOrProperties()
                    .hasFieldOrPropertyWithValue("id", storyId)
                    .hasFieldOrPropertyWithValue("title", story.getTitle())
                    .hasFieldOrPropertyWithValue("author", author.getFullName())
                    .hasFieldOrPropertyWithValue("publishedDate", clock.instant());
        });
    }

    private UserDto thereIsAnAuthor(TestUser author) {
        return accountModuleFacade.findOrCreate(new FindOrCreateAccountCommand(author.getId(), author.getName(), author.getLastName(), author.getEmail(), author.getPictureUrl()));
    }

    private String storyIsPublished(TestStory story) {
        return client.postForObject("/stories", authorized(new PublishNewStoryRequest(story.getTitle(), story.getContent())), PublishNewStoryResponse.class)
                .getStoryId();
    }

    @Test
    void shouldReturnStoryById() {
        // given
        var author = user().build();
        thereIsAnAuthor(author);
        fakeJwtDecoder.mockUser(author, MOCK_TOKEN);

        var story = story().build();
        var storyId = storyIsPublished(story);

        await().atMost(1, SECONDS).untilAsserted(() -> {
            // when
            var storyResponse = client.getForEntity("/public/stories/{storyId}", StoryViewResponse.class, storyId);

            // then
            assertThat(storyResponse.getStatusCode()).isEqualTo(OK);
            assertThat(storyResponse.getBody())
                    .hasNoNullFieldsOrProperties()
                    .hasFieldOrPropertyWithValue("title", story.getTitle())
                    .hasFieldOrPropertyWithValue("content", story.getContent())
                    .hasFieldOrPropertyWithValue("publishedDate", clock.instant())
                    .hasFieldOrPropertyWithValue("author", author.getFullName());
        });
    }

    @Test
    void shouldReturn404IfStoryDoesNotExist() {
        // given
        var notExistingStoryId = "1234";
        // when
        var storyResponse = client.getForEntity("/public/stories/${storyId}", GetStoriesForHomePageResponse.class, notExistingStoryId);

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
