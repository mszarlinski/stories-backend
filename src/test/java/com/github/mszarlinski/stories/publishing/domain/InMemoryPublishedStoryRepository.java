package com.github.mszarlinski.stories.publishing.domain;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

class InMemoryPublishedStoryRepository implements PublishedStoryRepository {
    private final List<PublishedStory> db = new ArrayList<>();

    @Override
    public PublishedStory save(PublishedStory story) {
        db.add(story);
        return story;
    }

    @Override
    public List<PublishedStory> findByAuthorId(String authorId) {
        return db.stream()
                .filter(s -> authorId.equals(s.getAuthorId()))
                .collect(toUnmodifiableList());
    }
}
