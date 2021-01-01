package com.github.mszarlinski.stories.publishing.domain;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface PublishedStoryRepository extends Repository<PublishedStory, String> {

    PublishedStory save(PublishedStory story);

    List<PublishedStory> findByAuthorId(String authorId);
}
