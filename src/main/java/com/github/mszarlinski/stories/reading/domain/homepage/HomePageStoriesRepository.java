package com.github.mszarlinski.stories.reading.domain.homepage;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface HomePageStoriesRepository extends Repository<HomePageStoryView, String> {
    HomePageStoryView save(HomePageStoryView homePageStoryView);

    List<HomePageStoryView> findAll();
}
