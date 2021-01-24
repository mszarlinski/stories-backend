package com.github.mszarlinski.stories.reading.domain;

import com.github.mszarlinski.stories.auth.AuthenticationModuleFacade;
import com.github.mszarlinski.stories.sharedkernel.StoryId;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
public class StoryReaderFacade {

    private final StoryViewReadModel storyViewReadModel;
    private final AuthenticationModuleFacade authenticationModuleFacade;

    public StoryReaderFacade(StoryViewReadModel storyViewReadModel,
                             AuthenticationModuleFacade authenticationModuleFacade) {
        this.storyViewReadModel = storyViewReadModel;
        this.authenticationModuleFacade = authenticationModuleFacade;
    }

    public List<StoryView> getStoriesForHomePage() {
        return storyViewReadModel.getStoriesForHomePage();
    }

    public void saveStoryInReadModels(StoryId storyId, String title, String content, String authorId, Instant publishedDate) {
        var author = authenticationModuleFacade.findUserById(authorId).orElseThrow();
        storyViewReadModel.save(storyId, title, content, author, publishedDate);
    }

    public Optional<StoryView> findStoryById(StoryId storyId) {
        return storyViewReadModel.findById(storyId);
    }
}
