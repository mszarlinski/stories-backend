package com.github.mszarlinski.stories.publishing;

import com.github.mszarlinski.stories.AcceptanceTests;
import com.github.mszarlinski.stories.publishing.ui.GetStoriesForPublisherResponse;
import com.github.mszarlinski.stories.publishing.ui.PublishNewStoryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;

import static com.github.mszarlinski.stories.test.SecurityUtils.authorized;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;

class PublishingAcceptanceTests extends AcceptanceTests {

    @Test
    void shouldPublishAndRetrieveNewStory() {
        // given
        String title = "Some title";
        String content = "Some content";

        fakeJwtDecoder.mockUser();

        // when
        var storiesResponse = client.postForEntity("/stories", authorized(new PublishNewStoryRequest(title, content)), Void.class);

        // then
        assertThat(storiesResponse.getStatusCode()).isEqualTo(CREATED);

        // when
        var publisherStoriesResponse =
                client.exchange("/publisher/stories", HttpMethod.GET, authorized(), GetStoriesForPublisherResponse.class).getBody();

        // then
        assertThat(publisherStoriesResponse.getStories())
                .singleElement()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrProperty("id")
                .hasFieldOrPropertyWithValue("title", title)
                .hasFieldOrPropertyWithValue("publishedDate", clock.instant());
    }

    @Test
    void shouldNotRetrieveSomeoneElseStory() {
        // given
        String title = "Some title";
        String content = "Some content";

        fakeJwtDecoder.mockUser("user1", "token1"); //TODO: DSL?
        fakeJwtDecoder.mockUser("user2", "token2");

        // when
        var storiesResponse = client.postForEntity("/stories", authorized(new PublishNewStoryRequest(title, content), "token1"), Void.class);

        // then
        assertThat(storiesResponse.getStatusCode()).isEqualTo(CREATED);

        // when
        var publisherStoriesResponse =
                client.exchange("/publisher/stories", HttpMethod.GET, authorized("token2"), GetStoriesForPublisherResponse.class).getBody();

        // then
        assertThat(publisherStoriesResponse.getStories()).isEmpty();
    }
}
