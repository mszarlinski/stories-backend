package com.github.mszarlinski.stories.reading.domain.storyview;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface StoryViewRepository extends Repository<StoryView, String> {
    StoryView save(StoryView storyView);

    Optional<StoryView> findById(String id);
}
