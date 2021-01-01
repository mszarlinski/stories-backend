package com.github.mszarlinski.stories.account;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountModuleFacade {
    public static final UserDto FAKE_USER = new UserDto("Jan", "Kowalski");

    public Optional<UserDto> getUserById(String userId) {
        //FIXME
        return Optional.of(FAKE_USER);
    }
}
