package com.github.mszarlinski.stories.reading.infrastructure;

import com.github.mszarlinski.stories.publishing.domain.StoryPublished;
import com.github.mszarlinski.stories.reading.domain.StoryReaderFacade;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
class StoryPublishedEventHandler {

    private final StoryReaderFacade storyReaderFacade;

    StoryPublishedEventHandler(StoryReaderFacade storyReaderFacade) {
        this.storyReaderFacade = storyReaderFacade;
    }

    @Async //TODO thread pool
    @EventListener
    public void onStoryPublished(StoryPublished event) {
        storyReaderFacade.saveStoryInReadModels(event.storyId(), event.title(), event.content(), event.authorId(), event.publishedDate());
    }
}
