package com.github.mszarlinski.stories.reading.domain;

import com.github.mszarlinski.stories.reading.domain.homepage.HomePageStoriesRepository;
import com.github.mszarlinski.stories.reading.domain.homepage.HomePageStoryView;

import java.util.ArrayList;
import java.util.List;

class InMemoryHomePageStoryRepository implements HomePageStoriesRepository {
    private final List<HomePageStoryView> db = new ArrayList<>();

    @Override
    public HomePageStoryView save(HomePageStoryView story) {
        db.add(story);
        return story;
    }

    @Override
    public List<HomePageStoryView> findAll() {
        return List.copyOf(db);
    }
}
