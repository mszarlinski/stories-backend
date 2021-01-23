package com.github.mszarlinski.stories.reading.domain;

import com.github.mszarlinski.stories.account.AccountModuleFacade;
import com.github.mszarlinski.stories.sharedkernel.StoryId;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
public class StoryReaderFacade {

    private final StoryViewReadModel storyViewReadModel;
    private final AccountModuleFacade accountModuleFacade;

    public StoryReaderFacade(StoryViewReadModel storyViewReadModel,
                             AccountModuleFacade accountModuleFacade) {
        this.storyViewReadModel = storyViewReadModel;
        this.accountModuleFacade = accountModuleFacade;
    }

    public List<StoryView> getStoriesForHomePage() {
        return storyViewReadModel.getStoriesForHomePage();
    }

    public void saveStoryInReadModels(StoryId storyId, String title, String content, String authorId, Instant publishedDate) {
        var author = accountModuleFacade.findAccountById(authorId).orElseThrow();
        storyViewReadModel.save(storyId, title, content, author, publishedDate);
    }

    public Optional<StoryView> findStoryById(StoryId storyId) {
        return storyViewReadModel.findById(storyId);
    }
}
