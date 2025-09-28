package com.github.mszarlinski.stories.publishing;

import com.github.mszarlinski.stories.AcceptanceTests;
import com.github.mszarlinski.stories.publishing.ui.GetStoriesForPublisherResponse;
import com.github.mszarlinski.stories.publishing.ui.PublishNewStoryRequest;
import com.github.mszarlinski.stories.publishing.ui.PublishNewStoryResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static com.github.mszarlinski.stories.test.SecurityUtils.authorized;
import static com.github.mszarlinski.stories.test.builder.TestUser.user;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

class PublishingAcceptanceTestsClaudeCode extends AcceptanceTests {

    @Test
    void shouldRejectPublishingWithoutAuthentication() {
        // given
        String title = "Some title";
        String content = "Some content";

        // when
        var response = client.postForEntity("/stories", new PublishNewStoryRequest(title, content), Void.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(UNAUTHORIZED);
    }

    @Test
    void shouldRejectPublishingStoryWithNullTitle() {
        // given
        String content = "Some content";
        fakeJwtDecoder.mockUser();

        // when
        var response = client.postForEntity("/stories", authorized(new PublishNewStoryRequest(null, content)), Void.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    void shouldRejectPublishingStoryWithEmptyTitle() {
        // given
        String title = "";
        String content = "Some content";
        fakeJwtDecoder.mockUser();

        // when
        var response = client.postForEntity("/stories", authorized(new PublishNewStoryRequest(title, content)), Void.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    void shouldRejectPublishingStoryWithNullContent() {
        // given
        String title = "Some title";
        fakeJwtDecoder.mockUser();

        // when
        var response = client.postForEntity("/stories", authorized(new PublishNewStoryRequest(title, null)), Void.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    void shouldRejectPublishingStoryWithEmptyContent() {
        // given
        String title = "Some title";
        String content = "";
        fakeJwtDecoder.mockUser();

        // when
        var response = client.postForEntity("/stories", authorized(new PublishNewStoryRequest(title, content)), Void.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    void shouldPublishStoryWithLongTitle() {
        // given
        String longTitle = "A".repeat(1000);
        String content = "Some content";
        fakeJwtDecoder.mockUser();

        // when
        var response = client.postForEntity("/stories", authorized(new PublishNewStoryRequest(longTitle, content)), PublishNewStoryResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(CREATED);

        // verify story is retrievable
        var publisherStoriesResponse =
                client.exchange("/publisher/stories", HttpMethod.GET, authorized(), GetStoriesForPublisherResponse.class).getBody();

        assertThat(publisherStoriesResponse.getStories())
                .singleElement()
                .hasFieldOrPropertyWithValue("title", longTitle);
    }

    @Test
    void shouldPublishStoryWithLongContent() {
        // given
        String title = "Some title";
        String longContent = "Lorem ipsum ".repeat(10000);
        fakeJwtDecoder.mockUser();

        // when
        var response = client.postForEntity("/stories", authorized(new PublishNewStoryRequest(title, longContent)), PublishNewStoryResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody().getStoryId()).isNotBlank();
    }

    @Test
    void shouldPublishStoryWithSpecialCharacters() {
        // given
        String title = "Title with special chars: áéíóú ñ ç €$£¥ @#%&*";
        String content = "Content with special chars: áéíóú ñ ç €$£¥ @#%&*\nNew line\tTab";
        fakeJwtDecoder.mockUser();

        // when
        var response = client.postForEntity("/stories", authorized(new PublishNewStoryRequest(title, content)), PublishNewStoryResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(CREATED);

        // verify story is retrievable
        var publisherStoriesResponse =
                client.exchange("/publisher/stories", HttpMethod.GET, authorized(), GetStoriesForPublisherResponse.class).getBody();

        assertThat(publisherStoriesResponse.getStories())
                .singleElement()
                .hasFieldOrPropertyWithValue("title", title);
    }

    @Test
    void shouldPublishMultipleStoriesBySameAuthor() {
        // given
        fakeJwtDecoder.mockUser();

        // when
        client.postForEntity("/stories", authorized(new PublishNewStoryRequest("Title 1", "Content 1")), PublishNewStoryResponse.class);
        client.postForEntity("/stories", authorized(new PublishNewStoryRequest("Title 2", "Content 2")), PublishNewStoryResponse.class);
        client.postForEntity("/stories", authorized(new PublishNewStoryRequest("Title 3", "Content 3")), PublishNewStoryResponse.class);

        // then
        var publisherStoriesResponse =
                client.exchange("/publisher/stories", HttpMethod.GET, authorized(), GetStoriesForPublisherResponse.class).getBody();

        assertThat(publisherStoriesResponse.getStories())
                .hasSize(3)
                .extracting("title")
                .containsExactlyInAnyOrder("Title 1", "Title 2", "Title 3");
    }

    @Test
    void shouldIsolateStoriesBetweenDifferentAuthors() {
        // given
        var user1 = user().build();
        var user2 = user().build();
        var user3 = user().build();

        fakeJwtDecoder.mockUser(user1, "token1");
        fakeJwtDecoder.mockUser(user2, "token2");
        fakeJwtDecoder.mockUser(user3, "token3");

        // when - each user publishes stories
        client.postForEntity("/stories", authorized(new PublishNewStoryRequest("User1 Story1", "Content1"), "token1"), PublishNewStoryResponse.class);
        client.postForEntity("/stories", authorized(new PublishNewStoryRequest("User1 Story2", "Content2"), "token1"), PublishNewStoryResponse.class);

        client.postForEntity("/stories", authorized(new PublishNewStoryRequest("User2 Story1", "Content3"), "token2"), PublishNewStoryResponse.class);

        client.postForEntity("/stories", authorized(new PublishNewStoryRequest("User3 Story1", "Content4"), "token3"), PublishNewStoryResponse.class);
        client.postForEntity("/stories", authorized(new PublishNewStoryRequest("User3 Story2", "Content5"), "token3"), PublishNewStoryResponse.class);
        client.postForEntity("/stories", authorized(new PublishNewStoryRequest("User3 Story3", "Content6"), "token3"), PublishNewStoryResponse.class);

        // then - each user sees only their own stories
        var user1Stories = client.exchange("/publisher/stories", HttpMethod.GET, authorized("token1"), GetStoriesForPublisherResponse.class).getBody();
        assertThat(user1Stories.getStories())
                .hasSize(2)
                .extracting("title")
                .containsExactlyInAnyOrder("User1 Story1", "User1 Story2");

        var user2Stories = client.exchange("/publisher/stories", HttpMethod.GET, authorized("token2"), GetStoriesForPublisherResponse.class).getBody();
        assertThat(user2Stories.getStories())
                .hasSize(1)
                .extracting("title")
                .containsExactly("User2 Story1");

        var user3Stories = client.exchange("/publisher/stories", HttpMethod.GET, authorized("token3"), GetStoriesForPublisherResponse.class).getBody();
        assertThat(user3Stories.getStories())
                .hasSize(3)
                .extracting("title")
                .containsExactlyInAnyOrder("User3 Story1", "User3 Story2", "User3 Story3");
    }

    @Test
    void shouldReturnEmptyListWhenAuthorHasNoStories() {
        // given
        fakeJwtDecoder.mockUser();

        // when
        var publisherStoriesResponse =
                client.exchange("/publisher/stories", HttpMethod.GET, authorized(), GetStoriesForPublisherResponse.class).getBody();

        // then
        assertThat(publisherStoriesResponse.getStories()).isEmpty();
    }

    @Test
    void shouldRejectGetPublisherStoriesWithoutAuthentication() {
        // when
        ResponseEntity<GetStoriesForPublisherResponse> response =
                client.exchange("/publisher/stories", HttpMethod.GET, null, GetStoriesForPublisherResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(UNAUTHORIZED);
    }

    @Test
    void shouldReturnUniqueStoryIdsForEachPublication() {
        // given
        fakeJwtDecoder.mockUser();

        // when
        var response1 = client.postForEntity("/stories", authorized(new PublishNewStoryRequest("Title", "Content")), PublishNewStoryResponse.class);
        var response2 = client.postForEntity("/stories", authorized(new PublishNewStoryRequest("Title", "Content")), PublishNewStoryResponse.class);
        var response3 = client.postForEntity("/stories", authorized(new PublishNewStoryRequest("Title", "Content")), PublishNewStoryResponse.class);

        // then
        var id1 = response1.getBody().getStoryId();
        var id2 = response2.getBody().getStoryId();
        var id3 = response3.getBody().getStoryId();

        assertThat(id1).isNotEqualTo(id2);
        assertThat(id1).isNotEqualTo(id3);
        assertThat(id2).isNotEqualTo(id3);

        // verify all stories are retrievable
        var publisherStoriesResponse =
                client.exchange("/publisher/stories", HttpMethod.GET, authorized(), GetStoriesForPublisherResponse.class).getBody();

        assertThat(publisherStoriesResponse.getStories())
                .hasSize(3)
                .extracting("id")
                .containsExactlyInAnyOrder(id1, id2, id3);
    }

    @Test
    void shouldSetCorrectPublishedDateForStories() {
        // given
        fakeJwtDecoder.mockUser();

        // when
        client.postForEntity("/stories", authorized(new PublishNewStoryRequest("Title", "Content")), PublishNewStoryResponse.class);

        // then
        var publisherStoriesResponse =
                client.exchange("/publisher/stories", HttpMethod.GET, authorized(), GetStoriesForPublisherResponse.class).getBody();

        assertThat(publisherStoriesResponse.getStories())
                .singleElement()
                .hasFieldOrPropertyWithValue("publishedDate", clock.instant());
    }

    @Test
    void shouldRejectPublishingStoryWithBlankTitle() {
        // given
        String title = "   \t\n  ";
        String content = "Some content";
        fakeJwtDecoder.mockUser();

        // when
        var response = client.postForEntity("/stories", authorized(new PublishNewStoryRequest(title, content)), Void.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    void shouldRejectPublishingStoryWithBlankContent() {
        // given
        String title = "Some title";
        String content = "   \t\n  ";
        fakeJwtDecoder.mockUser();

        // when
        var response = client.postForEntity("/stories", authorized(new PublishNewStoryRequest(title, content)), Void.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }
}