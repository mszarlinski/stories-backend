package com.github.mszarlinski.stories.reading.ui;

import com.github.mszarlinski.stories.reading.domain.StoryReaderFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.stream.Collectors.toList;

@RestController
class HomePageController {

    private final StoryReaderFacade storyReaderFacade;

    HomePageController(StoryReaderFacade storyReaderFacade) {
        this.storyReaderFacade = storyReaderFacade;
    }

    @GetMapping("/home/stories")
    GetStoriesForHomePageResponse getStoriesForHomePageResponse() {
        return new GetStoriesForHomePageResponse(
                storyReaderFacade.getStoriesForHomePage()
                        .stream()
                        .map(s -> new HomePageStoryViewResponse(s.getTitle(), s.getAuthor(), s.getPublishedDate()))
                        .collect(toList()));
    }
}
