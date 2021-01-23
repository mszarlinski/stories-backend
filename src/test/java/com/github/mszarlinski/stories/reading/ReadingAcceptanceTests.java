package com.github.mszarlinski.stories.reading;

import com.github.mszarlinski.stories.AcceptanceTests;
import com.github.mszarlinski.stories.publishing.ui.PublishNewStoryRequest;
import com.github.mszarlinski.stories.publishing.ui.PublishNewStoryResponse;
import com.github.mszarlinski.stories.reading.ui.GetStoriesForHomePageResponse;
import com.github.mszarlinski.stories.reading.ui.StoryViewResponse;
import com.github.mszarlinski.stories.test.builder.TestStory;
import org.junit.jupiter.api.Test;

import static com.github.mszarlinski.stories.test.SecurityUtils.authorized;
import static com.github.mszarlinski.stories.test.builder.TestStory.story;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

class ReadingAcceptanceTests extends AcceptanceTests {
//
//    @Test
//    void shouldReturnStoriesForHomePage() {
//        // given
//        var publisher = user().build();
//
//        var story = story().withAuthorId(publisher.getId()).build();
//        var storyId = storyIsPublished(story);
//
//        await().atMost(1, SECONDS).untilAsserted(() -> {
//            // when
//            var storiesResponse = client
//                    .getForEntity("/public/home/stories", GetStoriesForHomePageResponse.class);
//
//            // then
//            assertThat(storiesResponse.getStatusCode()).isEqualTo(OK);
//            assertThat(storiesResponse.getBody().getStories())
//                    .singleElement()
//                    .hasNoNullFieldsOrProperties()
//                    .hasFieldOrPropertyWithValue("id", storyId)
//                    .hasFieldOrPropertyWithValue("title", story.getTitle())
//                    .hasFieldOrPropertyWithValue("author", String.format("%s %s", publisher.getName(), publisher.getLastName())) //FIXME
//                    .hasFieldOrPropertyWithValue("publishedDate", clock.instant());
//        });
//    }

    private String storyIsPublished(TestStory story) {
        fakeJwtDecoder.mockUser();
        return client.postForObject("/stories", authorized(new PublishNewStoryRequest(story.getTitle(), story.getContent())), PublishNewStoryResponse.class)
                .getStoryId();
    }

    @Test
    void shouldReturnStoryById() {
        // given
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
                    .hasFieldOrPropertyWithValue("author", String.format("%s %s", "X", "Y"));
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
