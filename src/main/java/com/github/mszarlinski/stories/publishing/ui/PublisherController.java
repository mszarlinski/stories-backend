package com.github.mszarlinski.stories.publishing.ui;

import com.github.mszarlinski.stories.publishing.application.StoryPublisherFacade;
import com.github.mszarlinski.stories.publishing.domain.InvalidStoryException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
class PublisherController {

    private final StoryPublisherFacade storyPublisherFacade;

    PublisherController(StoryPublisherFacade storyPublisherFacade) {
        this.storyPublisherFacade = storyPublisherFacade;
    }

    @PostMapping("/stories")
    ResponseEntity<PublishNewStoryResponse> publishNewStory(
            @RequestBody PublishNewStoryRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        try {
            var storyId = storyPublisherFacade.publish(request.getTitle(), request.getContent(), jwt.getSubject());
            return ResponseEntity.status(CREATED).body(new PublishNewStoryResponse(storyId.value()));
        } catch (InvalidStoryException e) {
            return ResponseEntity.status(BAD_REQUEST).build();
        }
    }

    @PutMapping("/stories")
    void updateStory(@RequestBody PublishNewStoryRequest request) {

    }
}

