package com.github.mszarlinski.stories.reading.domain.storyview;

import com.github.mszarlinski.stories.account.UserDto;
import com.github.mszarlinski.stories.common.StoryId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

import static com.github.mszarlinski.stories.reading.domain.UserExt.fullName;

@Component
public class StoryViewReadModel {

    @Autowired
    MongoTemplate mongo;

    private final StoryViewRepository storyViewRepository;

    public StoryViewReadModel(StoryViewRepository storyViewRepository) {
        this.storyViewRepository = storyViewRepository;
    }

    public Optional<StoryView> findById(StoryId storyId) {
        return storyViewRepository.findById(storyId.value());
    }

    public void save(StoryId storyId, String title, String content, UserDto author, Instant publishedDate) {
        storyViewRepository.save(new StoryView(storyId.value(), title, fullName(author), content, publishedDate));
    }
}
