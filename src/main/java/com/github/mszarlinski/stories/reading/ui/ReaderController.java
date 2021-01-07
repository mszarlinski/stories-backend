package com.github.mszarlinski.stories.reading.ui;

import com.github.mszarlinski.stories.reading.domain.StoryReaderFacade;
import com.github.mszarlinski.stories.sharedkernel.StoryId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static java.util.stream.Collectors.toList;

@RestController
class ReaderController {

    private final StoryReaderFacade storyReaderFacade;

    ReaderController(StoryReaderFacade storyReaderFacade) {
        this.storyReaderFacade = storyReaderFacade;
    }

    @GetMapping("/home/stories")
    GetStoriesForHomePageResponse getStoriesForHomePageResponse() {
        return new GetStoriesForHomePageResponse(
                storyReaderFacade.getStoriesForHomePage()
                        .stream()
                        .map(s -> new HomePageStoryViewResponse(s.getId(), s.getTitle(), s.getAuthor(), s.getPublishedDate()))
                        .collect(toList()));
    }

    @GetMapping("/stories/{storyId}")
    ResponseEntity<StoryViewResponse> getStoryById(@PathVariable String storyId) {
        return ResponseEntity.of(storyReaderFacade.findStoryById(StoryId.of(storyId))
                .map(s -> new StoryViewResponse(s.getTitle(), s.getContent(), s.getAuthor(), s.getPublishedDate())));
    }
}
