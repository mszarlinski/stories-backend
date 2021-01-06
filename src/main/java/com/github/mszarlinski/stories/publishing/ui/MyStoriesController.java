package com.github.mszarlinski.stories.publishing.ui;

import com.github.mszarlinski.stories.publishing.FakeAuthor;
import com.github.mszarlinski.stories.publishing.application.StoryPublisherFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.stream.Collectors.toList;

@RestController
class MyStoriesController {

    private final StoryPublisherFacade storyPublisherFacade;

    MyStoriesController(StoryPublisherFacade storyPublisherFacade) {
        this.storyPublisherFacade = storyPublisherFacade;
    }

    @GetMapping("/publisher/stories")
    GetStoriesForPublisherResponse getStoriesForPublisher() {
        return new GetStoriesForPublisherResponse(
                storyPublisherFacade.getStories(FakeAuthor.ID)
                        .stream()
                        .map(s -> new PublishedStoryResponse(
                                s.getId().value(),
                                s.getTitle(),
                                s.getPublishedDate())
                        )
                        .collect(toList())
        );
    }
}
