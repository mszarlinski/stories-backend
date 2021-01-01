package com.github.mszarlinski.stories.reading.ui;

import com.github.mszarlinski.stories.common.StoryId;
import com.github.mszarlinski.stories.reading.domain.StoryReaderFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class StoryViewController {

    private final StoryReaderFacade storyReaderFacade;

    StoryViewController(StoryReaderFacade storyReaderFacade) {
        this.storyReaderFacade = storyReaderFacade;
    }

    @GetMapping("/stories/{storyId}")
    ResponseEntity<StoryViewResponse> getStoryById(@PathVariable String storyId) {
        return ResponseEntity.of(storyReaderFacade.findStoryById(StoryId.of(storyId))
                .map(s -> new StoryViewResponse(s.title(), s.content(), s.author(), s.publishedDate())));
    }
}