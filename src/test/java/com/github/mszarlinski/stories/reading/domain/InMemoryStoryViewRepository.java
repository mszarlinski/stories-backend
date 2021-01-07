package com.github.mszarlinski.stories.reading.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryStoryViewRepository implements StoryViewRepository {
    private final List<StoryView> db = new ArrayList<>();

    @Override
    public StoryView save(StoryView storyView) {
        db.add(storyView);
        return storyView;
    }

    @Override
    public Optional<StoryView> findById(String id) {
        return db.stream()
                .filter(s -> id.equals(s.getId()))
                .findFirst();
    }

    @Override
    public List<StoryView> findAll() {
        return List.copyOf(db);
    }
}
