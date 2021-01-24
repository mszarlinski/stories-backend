package com.github.mszarlinski.stories.auth.domain;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, String> {
    Optional<User> findById(String id);

    User save(User user);

}
