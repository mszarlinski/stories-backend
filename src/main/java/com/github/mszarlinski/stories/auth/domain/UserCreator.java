package com.github.mszarlinski.stories.auth.domain;

import com.github.mszarlinski.stories.auth.FindOrCreateUserCommand;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
public class UserCreator {
    private final UserRepository userRepository;
    private final Clock clock;

    UserCreator(UserRepository userRepository, Clock clock) {
        this.userRepository = userRepository;
        this.clock = clock;
    }

    public User createNewAccount(FindOrCreateUserCommand findOrCreateUserCommand) {
        return userRepository.save(new User(findOrCreateUserCommand.getUserId(), findOrCreateUserCommand.getName(), findOrCreateUserCommand.getLastName(),
                findOrCreateUserCommand.getEmail(), findOrCreateUserCommand.getPictureUrl(), clock.instant()));
    }
}
