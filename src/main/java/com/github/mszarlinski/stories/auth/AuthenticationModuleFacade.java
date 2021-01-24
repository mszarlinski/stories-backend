package com.github.mszarlinski.stories.auth;

import com.github.mszarlinski.stories.auth.domain.User;
import com.github.mszarlinski.stories.auth.domain.UserCreator;
import com.github.mszarlinski.stories.auth.domain.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationModuleFacade {

    private final UserRepository userRepository;
    private final UserCreator userCreator;

    AuthenticationModuleFacade(UserRepository userRepository, UserCreator userCreator) {
        this.userRepository = userRepository;
        this.userCreator = userCreator;
    }

    public Optional<UserDto> findUserById(String userId) {
        return userRepository.findById(userId)
                .map(UserDto::fromAccount);
    }

    public UserDto findOrCreate(FindOrCreateUserCommand findOrCreateUserCommand) {
        User user = userRepository.findById(findOrCreateUserCommand.getUserId())
                .orElseGet(() -> userCreator.createNewAccount(findOrCreateUserCommand));
        return UserDto.fromAccount(user);
    }
}
