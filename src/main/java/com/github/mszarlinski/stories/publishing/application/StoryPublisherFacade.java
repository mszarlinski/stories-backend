package com.github.mszarlinski.stories.publishing.application;

import com.github.mszarlinski.stories.publishing.domain.PublishedStory;
import com.github.mszarlinski.stories.publishing.domain.PublishedStoryRepository;
import com.github.mszarlinski.stories.sharedkernel.StoryId;
import com.github.mszarlinski.stories.sharedkernel.event.EventsPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@Component
public class StoryPublisherFacade {
    private final PublishedStoryRepository publishedStoryRepository;
    private final Clock clock;
    private final EventsPublisher eventsPublisher;

    @Autowired
    MongoTemplate mongo;

    public StoryPublisherFacade(PublishedStoryRepository publishedStoryRepository, Clock clock, EventsPublisher eventsPublisher) {
        this.publishedStoryRepository = publishedStoryRepository;
        this.clock = clock;
        this.eventsPublisher = eventsPublisher;
    }

    public StoryId publish(String title, String content, String authorId) {
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

    public List<StoryDto> getStories(String authorId) {
        return publishedStoryRepository.findByAuthorId(authorId)
                .stream()
                .map(PublishedStory::toDto)
                .collect(toUnmodifiableList());
    }
}
