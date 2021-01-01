package com.github.mszarlinski.stories.reading.domain.homepage;

import com.github.mszarlinski.stories.account.UserDto;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

import static com.github.mszarlinski.stories.reading.domain.UserExt.fullName;

@Component
public class ReaderHomePageReadModel {

    private final HomePageStoriesRepository homePageStoriesRepository;

    public ReaderHomePageReadModel(HomePageStoriesRepository homePageStoriesRepository) {
        this.homePageStoriesRepository = homePageStoriesRepository;
    }

    public void save(String title, UserDto author, Instant publishedDate) {
        homePageStoriesRepository.save(new HomePageStoryView(title, fullName(author), publishedDate));
    }

    public List<HomePageStoryView> getStoriesForHomePage() {
        return homePageStoriesRepository.findAll(); //TODO: paging & sorting
    }

}

