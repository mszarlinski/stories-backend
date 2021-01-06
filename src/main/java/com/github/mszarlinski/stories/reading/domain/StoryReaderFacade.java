package com.github.mszarlinski.stories.reading.domain;

import com.github.mszarlinski.stories.account.AccountModuleFacade;
import com.github.mszarlinski.stories.common.StoryId;
import com.github.mszarlinski.stories.reading.domain.homepage.HomePageStoryView;
import com.github.mszarlinski.stories.reading.domain.homepage.ReaderHomePageReadModel;
import com.github.mszarlinski.stories.reading.domain.storyview.StoryView;
import com.github.mszarlinski.stories.reading.domain.storyview.StoryViewReadModel;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
public class StoryReaderFacade {

    private final ReaderHomePageReadModel readerHomePageReadModel;
    private final StoryViewReadModel storyViewReadModel;
    private final AccountModuleFacade accountModuleFacade;

    public StoryReaderFacade(ReaderHomePageReadModel readerHomePageReadModel,
                             StoryViewReadModel storyViewReadModel,
                             AccountModuleFacade accountModuleFacade) {
        this.readerHomePageReadModel = readerHomePageReadModel;
        this.storyViewReadModel = storyViewReadModel;
        this.accountModuleFacade = accountModuleFacade;
    }

    public List<HomePageStoryView> getStoriesForHomePage() {
        return readerHomePageReadModel.getStoriesForHomePage();
    }

    public void saveStoryInReadModels(StoryId storyId, String title, String content, String authorId, Instant publishedDate) {
        var author = accountModuleFacade.getUserById(authorId).orElseThrow();
        readerHomePageReadModel.save(storyId, title, author, publishedDate);
        storyViewReadModel.save(storyId, title, content, author, publishedDate);
    }

    public Optional<StoryView> findStoryById(StoryId storyId) {
        return storyViewReadModel.findById(storyId);
    }
}
