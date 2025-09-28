package com.github.mszarlinski.stories.publishing.application;

import com.github.mszarlinski.stories.publishing.domain.PublishedStory;
import com.github.mszarlinski.stories.publishing.domain.PublishedStoryRepository;
import com.github.mszarlinski.stories.sharedkernel.StoryId;
import com.github.mszarlinski.stories.sharedkernel.event.EventsPublisher;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@Component
public class StoryPublisherFacade {
    private final PublishedStoryRepository publishedStoryRepository;
    private final Clock clock;
    private final EventsPublisher eventsPublisher;

    public StoryPublisherFacade(PublishedStoryRepository publishedStoryRepository, Clock clock, EventsPublisher eventsPublisher) {
        this.publishedStoryRepository = publishedStoryRepository;
        this.clock = clock;
        this.eventsPublisher = eventsPublisher;
    }

    public StoryId publish(String title, String content, String authorId) {
        validateInput(title, content, authorId);

        var story = new PublishedStory(
                title,
                content,
                authorId,
                clock.instant()
        );
        var savedStory = publishedStoryRepository.save(story);
        eventsPublisher.publish(savedStory.storyPublishedEvent());
        return savedStory.getId();
    }

    private void validateInput(String title, String content, String authorId) {
        if (title == null || title.trim().isEmpty()) {
            throw new com.github.mszarlinski.stories.publishing.domain.EmptyTitleException();
        }
        if (content == null || content.trim().isEmpty()) {
            throw new com.github.mszarlinski.stories.publishing.domain.EmptyContentException();
        }
        if (authorId == null || authorId.trim().isEmpty()) {
            throw new com.github.mszarlinski.stories.publishing.domain.InvalidAuthorException();
        }
    }

    public List<StoryDto> getStories(String authorId) {
        return publishedStoryRepository.findByAuthorId(authorId)
                .stream()
                .map(PublishedStory::toDto)
                .toList();
    }
}
