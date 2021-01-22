package com.github.mszarlinski.stories.publishing.ui;

import com.github.mszarlinski.stories.publishing.application.StoryPublisherFacade;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
class PublisherController {

    private final StoryPublisherFacade storyPublisherFacade;

    PublisherController(StoryPublisherFacade storyPublisherFacade) {
        this.storyPublisherFacade = storyPublisherFacade;
    }

    @ResponseStatus(CREATED)
    @PostMapping("/stories")
    PublishNewStoryResponse publishNewStory(
            @RequestBody PublishNewStoryRequest request,
            Principal principal) {
        var storyId = storyPublisherFacade.publish(request.getTitle(), request.getContent(), principal.getName());
        return new PublishNewStoryResponse(storyId.value());
    }

    @PutMapping("/stories")
    void updateStory(@RequestBody PublishNewStoryRequest request) {

    }
}

