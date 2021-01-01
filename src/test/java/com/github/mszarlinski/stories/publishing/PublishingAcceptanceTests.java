package com.github.mszarlinski.stories.publishing;

import com.github.mszarlinski.stories.AcceptanceTests;
import com.github.mszarlinski.stories.publishing.ui.GetStoriesForPublisherResponse;
import com.github.mszarlinski.stories.publishing.ui.PublishNewStoryRequest;
import com.github.mszarlinski.stories.publishing.ui.PublishedStoryResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;

class PublishingAcceptanceTests extends AcceptanceTests {

    @Test
    void shouldPublishAndRetrieveNewStory() {
        // given
        String title = "Some title";
        String content = "Some content";

        // when
        var storiesResponse = client.postForEntity("/stories", new PublishNewStoryRequest(title, content), Void.class);

        // then
        assertThat(storiesResponse.getStatusCode()).isEqualTo(CREATED);

        // when
        var publisherStoriesResponse = client.getForObject("/publisher/stories", GetStoriesForPublisherResponse.class);

        // then
        assertThat(publisherStoriesResponse.stories()).map(PublishedStoryResponse::title).contains(title);
    }
}
