package com.github.mszarlinski.stories.publishing.ui;

import com.github.mszarlinski.stories.publishing.application.StoryPublisherFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static java.util.stream.Collectors.toList;

@RestController
class MyStoriesController {

    private final StoryPublisherFacade storyPublisherFacade;

    MyStoriesController(StoryPublisherFacade storyPublisherFacade) {
        this.storyPublisherFacade = storyPublisherFacade;
    }

    @GetMapping("/publisher/stories")
    GetStoriesForPublisherResponse getStoriesForPublisher(Principal principal) {
        return new GetStoriesForPublisherResponse(
                storyPublisherFacade.getStories(principal.getName())
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
