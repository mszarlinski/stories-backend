package com.github.mszarlinski.stories.reading.domain;

import com.github.mszarlinski.stories.auth.UserDto;
import com.github.mszarlinski.stories.sharedkernel.StoryId;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.github.mszarlinski.stories.reading.domain.UserExt.fullName;

@Component
public class StoryViewReadModel {

    private final StoryViewRepository storyViewRepository;

    public StoryViewReadModel(StoryViewRepository storyViewRepository) {
        this.storyViewRepository = storyViewRepository;
    }

    public Optional<StoryView> findById(StoryId storyId) {
        return storyViewRepository.findById(storyId.value());
    }

    public List<StoryView> getStoriesForHomePage() {
        return storyViewRepository.findAll(); //TODO: paging & sorting
    }

    public void save(StoryId storyId, String title, String content, UserDto author, Instant publishedDate) {
        storyViewRepository.save(new StoryView(storyId.value(), title, fullName(author), content, publishedDate));
    }
}
